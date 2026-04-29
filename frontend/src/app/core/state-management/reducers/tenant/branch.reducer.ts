import { createReducer, on } from '@ngrx/store';
import * as BranchActions from '../../actions/tenant/branch.actions';
import { Branch } from 'src/app/core/models/branch.model';

export interface BranchState {
  loading: boolean;
  error: string | null;
  branches: Branch[];
  selectedBranch: Branch | null;
}

export const initialState: BranchState = {
  loading: false,
  error: null,
  branches: [],
  selectedBranch: null
};

export const branchReducer = createReducer(
  initialState,
  on(BranchActions.loadBranches, state => ({ ...state, loading: true, error: null })),
  on(BranchActions.loadBranchesSuccess, (state, { branches }) => ({ ...state, loading: false, branches })),
  on(BranchActions.loadBranchesFailure, (state, { error }) => ({ ...state, loading: false, error })),

  on(BranchActions.createBranch, state => ({ ...state, loading: true, error: null })),
  on(BranchActions.createBranchSuccess, (state, { branch  }) => ({
    ...state,
    loading: false,
    branches: [...state.branches, branch]
  })),
  on(BranchActions.createBranchFailure, (state, { error }) => ({ ...state, loading: false, error })),

  on(BranchActions.updateBranch, state => ({ ...state, loading: true, error: null })),
  on(BranchActions.updateBranchSuccess, (state, { branch  }) => ({
    ...state,
    loading: false,
    branches: state.branches.map(b => b.id === branch.id ? branch: b)
  })),
  on(BranchActions.updateBranchFailure, (state, { error }) => ({ ...state, loading: false, error })),

  on(BranchActions.deleteBranch, state => ({ ...state, loading: true, error: null })),
  on(BranchActions.deleteBranchSuccess, (state, { branchId }) => ({
    ...state,
    branches: state.branches.map(b => 
      b.id === branchId ? { ...b, active: false } : b
    )
  })),
  on(BranchActions.deleteBranchFailure, (state, { error }) => ({ ...state, loading: false, error })),

  on(BranchActions.loadBranchById, state => ({ ...state, loading: true, error: null })),
  on(BranchActions.loadBranchByIdSuccess, (state, { branch }) => ({
    ...state,
    selectedBranch: branch
  })),
  on(BranchActions.loadBranchByIdFailure, (state, { error }) => ({ ...state, loading: false, error })),

  on(BranchActions.clearSelectedBranch, state => ({ ...state, selectedBranch: null })),
  on(BranchActions.setSelectedBranch, (state, { branch }) => ({
      ...state,
      selectedBranch: branch
    }))
);