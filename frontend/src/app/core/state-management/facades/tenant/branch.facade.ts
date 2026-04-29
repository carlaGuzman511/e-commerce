import { Injectable } from '@angular/core';
import { Store as NgRxStore, select } from '@ngrx/store';
import { Observable } from 'rxjs';
import { Branch } from 'src/app/core/models/branch.model';
import * as BranchActions from '../../actions/tenant/branch.actions';
import { selectBranches, selectLoading, selectSelectedBranch } from '../../selectors/tenant/branch.selectors';

@Injectable({
  providedIn: 'root'
})

export class BranchFacade {
  public branches$: Observable<Branch[]>;
  public selectedBranch$: Observable<Branch | null>;
  public loading$: Observable<boolean>;

  constructor(private _store: NgRxStore) {
    this.branches$ = this._store.pipe(select(selectBranches));
    this.selectedBranch$ = this._store.pipe(select(selectSelectedBranch));
    this.loading$ = this._store.pipe(select(selectLoading));
  }

  public loadBranches() {
    this._store.dispatch(BranchActions.loadBranches());
  }

  public loadBranchById(branchId: string): void {
    this._store.dispatch(BranchActions.loadBranchById({ branchId }));
  }

  public createBranch(branch: Branch): void {
    this._store.dispatch(BranchActions.createBranch({ branch }));
  }

  public updateBranch(branch: Branch): void {
    this._store.dispatch(BranchActions.updateBranch({ branch }));
  }

  public deleteBranch(branchId: string): void {
    this._store.dispatch(BranchActions.deleteBranch({ branchId }));
  }

  public clearSelectedBranch(): void {
    this._store.dispatch(BranchActions.clearSelectedBranch());
  }

  public setSelectedBranch(branch: Branch | null): void {
    this._store.dispatch(BranchActions.setSelectedBranch({ branch }));
  }
}