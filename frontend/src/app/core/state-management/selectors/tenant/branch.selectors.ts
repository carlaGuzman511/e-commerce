import { createSelector, createFeatureSelector } from '@ngrx/store';
import { BranchState } from '../../state/branch.state';

export const selectBranchState = createFeatureSelector<BranchState>('branch');

export const selectLoading = createSelector(
  selectBranchState,
  state => state.loading
);

export const selectSelectedBranch = createSelector(
  selectBranchState,
  state => state.selectedBranch
)

export const selectBranches = createSelector(
  selectBranchState,
  state => state.branches
);

export const selectError = createSelector(
  selectBranchState,
  state => state.error
);