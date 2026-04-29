import { Component, ChangeDetectionStrategy, ChangeDetectorRef, OnInit } from '@angular/core';
import { ButtonType } from 'src/app/shared/enums/button-type.enum';
import { ModalMode } from 'src/app/core/enums/modal-mode.enum';
import { EMPTY } from 'src/app/shared/constants/app-const.constant';
import { BranchFacade } from 'src/app/core/state-management/facades/tenant/branch.facade';
import { Branch } from 'src/app/core/models/branch.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-branch-container',
  templateUrl: './branch-container.component.html',
  styleUrls: ['./branch-container.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class BranchContainerComponent implements OnInit {
  public readonly buttonType = ButtonType;
  
  public isModalOpen: boolean;
  public isModalMode: ModalMode;
  public query: string;

  constructor(
    private _branchFacade: BranchFacade,
    private _cd: ChangeDetectorRef
  ) {
    this.isModalOpen = false;
    this.isModalMode = ModalMode.Create;
    this.query = EMPTY;
  }

  ngOnInit(): void {
    this._initialize();
  }

  public openModal(): void {
    this.isModalMode = ModalMode.Create;
    this._branchFacade.clearSelectedBranch();
    this.isModalOpen = true;
    this._cd.markForCheck();
  }

  public onModalSubmit(branch: Branch): void {
    if (this.isModalMode === ModalMode.Create) {
      this._branchFacade.createBranch(branch);
    } else if (this.isModalMode === ModalMode.Edit) {
      this._branchFacade.updateBranch(branch);
    }
  }

  public onModalCancel(): void { 
    this.isModalOpen = false; 
    this._cd.markForCheck(); 
  }

  public onView(branch: Branch): void {
    if (!branch.id) return;

    this.isModalMode = ModalMode.View;
    this._branchFacade.setSelectedBranch(branch);
    this.isModalOpen = true;
    this._cd.markForCheck();
  }

  public onEdit(branch: Branch): void {
    if (!branch.id) return;

    this.isModalMode = ModalMode.Edit;
    this._branchFacade.setSelectedBranch(branch);
    this.isModalOpen = true;
    this._cd.markForCheck();
  }

  public onRemove(branch: Branch): void {
    if (!branch.id) return;

    if (confirm(`Are you sure you want to delete it?, ${branch.name}?`)) {
      this._branchFacade.deleteBranch(branch.id);
    }
  }

  public get branches$(): Observable<Branch[]> {
    return this._branchFacade.branches$;
  }

  public get selectedBranch$(): Observable<Branch | null> {
    return this._branchFacade.selectedBranch$;
  }

  public get loading$(): Observable<boolean> {
    return this._branchFacade.loading$;
  }

  public trackByBranchId(index: number, branch: Branch): string | undefined{
    return branch?.id;
  }

  private _initialize(): void {
    this._branchFacade.loadBranches();
  }
}