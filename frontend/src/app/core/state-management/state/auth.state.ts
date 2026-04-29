import {SimpleUserResponse} from '../../models/response/auth/simple-user-response.model';

export interface AuthState {
  user: SimpleUserResponse | null;
  accessToken: string | null;
  isAuthenticated: boolean;
  loading: boolean;
  error: string | null;
}

export const initialState: AuthState = {
  user: null,
  accessToken: null,
  isAuthenticated: false,
  loading: false,
  error: null
};
