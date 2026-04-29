import { createAction, props } from '@ngrx/store';
import { Branch } from 'src/app/core/models/branch.model';

export const createBranch = createAction(
  '[Branch] Create Branch',
  props<{ branch: Branch }>()
);

export const createBranchSuccess = createAction(
  '[Branch] Create Branch Success',
  props<{ branch: Branch }>()
);

export const createBranchFailure = createAction(
  '[Branch] Create Branch Failure',
  props<{ error: string }>()
);

export const loadBranches = createAction(
  '[Branch] Load Branches'
);

export const loadBranchesSuccess = createAction(
  '[Branch] Load Branches Success', 
  props<{ branches: Branch[] }>()
);

export const loadBranchesFailure = createAction(
  '[Branch] Load Branches Failure',
  props<{ error: string }>()
);

export const loadBranchById = createAction(
  '[Branch] Load Branch By Id',
  props<{ branchId: string }>()
);

export const loadBranchByIdSuccess = createAction(
  '[Branch] Load Branch By Id Success',
  props<{ branch: Branch }>()
);

export const loadBranchByIdFailure = createAction(
  '[Branch] Load Branch By Id Failure',
  props<{ error: string }>()
);

export const updateBranch = createAction(
  '[Branch] Update Branch',
  props<{ branch: Branch }>()
);

export const updateBranchSuccess = createAction(
  '[Branch] Update Branch Success',
  props<{ branch: Branch }>()
);

export const updateBranchFailure = createAction(
  '[Branch] Update Branch Failure',
  props<{ error: string }>()
);

export const deleteBranch = createAction(
  '[Branch] Delete Branch',
  props<{ branchId: string }>()
);

export const deleteBranchSuccess = createAction(
  '[Branch] Delete Branch Success',
  props<{ branchId: string }>()
);

export const deleteBranchFailure = createAction(
  '[Branch] Delete Branch Failure',
  props<{ error: string }>()
);

export const clearSelectedBranch = createAction(
  '[Branch] Clear Selected Branch'
);

export const setSelectedBranch = createAction(
  '[Branch] Set Selected Branch',
  props<{ branch: Branch | null }>()
);