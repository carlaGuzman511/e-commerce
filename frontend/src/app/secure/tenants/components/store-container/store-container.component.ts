import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Store } from 'src/app/core/models/store.model';
import { ButtonType } from 'src/app/shared/enums/button-type.enum';
import { ModalMode } from 'src/app/core/enums/modal-mode.enum';
import { EMPTY } from 'src/app/shared/constants/app-const.constant';
import { StoreFacade } from 'src/app/core/state-management/facades/tenant/store.facade';

@Component({
  selector: 'app-store-container',
  templateUrl: './store-container.component.html',
  styleUrls: ['./store-container.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class StoreContainerComponent implements OnInit {
  public readonly buttonType;
  public isModalOpen: boolean;
  public isModalMode: ModalMode;
  public query: string;

  constructor(
    private _storeFacade: StoreFacade,
    private _cd: ChangeDetectorRef
  ) {
    this.buttonType = ButtonType;
    this.isModalOpen = false;
    this.isModalMode = ModalMode.Create;
    this.query = EMPTY;
  }

  public ngOnInit(): void {
    this._initialize();
  }

  public openModal(): void {
    this.isModalMode = ModalMode.Create;
    this._storeFacade.setSelectedStore(null);
    this.isModalOpen = true;
    this._cd.markForCheck();
  }

  public onModalSubmit(store: Store): void {
    if (this.isModalMode === ModalMode.Create) {
      this._storeFacade.createStore(store);
    } else if (this.isModalMode === ModalMode.Edit) {
      this._storeFacade.updateStore(store);
    }
  }

  public onModalCancel(): void { 
    this.isModalOpen = false; 
    this._cd.markForCheck(); 
  }

  public onView(store: Store) {
    if (!store.id) return;

    this.isModalMode = ModalMode.View;
    this._storeFacade.setSelectedStore(store);
    this.isModalOpen = true;
    this._cd.markForCheck();
  }

  public onEdit(store: Store) {
    if (!store.id) return;

    this.isModalMode = ModalMode.Edit;
    this._storeFacade.setSelectedStore(store);
    this.isModalOpen = true;
    this._cd.markForCheck();
  }

  public onRemove(store: Store): void {
    if (!store.id) return;

    if (confirm(`Are you sure you want to delete it?, ${store.name}?`)) {
      this._storeFacade.deleteStore(store.id);
    }
  }

  public get stores$() {
    return this._storeFacade.stores$;
  }

  public get selectedStore$() {
    return this._storeFacade.selectedStore$;
  }

  public get loading$() {
    return this._storeFacade.loading$;
  }

  public trackByStoreId(index: number, store: Store): string | undefined {
    return store?.id;
  }

  private _initialize(): void {
    this._storeFacade.loadStores();
  }
}
