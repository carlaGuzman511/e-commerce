import { Branch } from '../../models/branch.model';
import { EMPTY } from 'src/app/shared/constants/app-const.constant';

export interface BranchState {
  loading: boolean;
  error: string | null;
  branches: Branch[];
  selectedBranch: Branch | null;
}

export const initialState: BranchState = {
  loading: false,
  error: EMPTY,
  branches: [],
  selectedBranch: null
};