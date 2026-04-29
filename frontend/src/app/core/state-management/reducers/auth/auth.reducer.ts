import {createReducer, on} from '@ngrx/store';
import {AuthState, initialState} from '../../state/auth.state';
import * as AuthActions from '../../actions/auth/auth.action';

export const authReducer = createReducer(
  initialState,

  on(
    AuthActions.login,
    (state): AuthState => ({
      ...state,
      loading: true,
      error: null
    })
  ),

  on(
    AuthActions.loginSuccess,
    (state, {authResponse}): AuthState => ({
      ...state,
      user: authResponse.user,
      accessToken: authResponse.accessToken,
      isAuthenticated: true,
      loading: false,
      error: null
    })
  ),

  on(
    AuthActions.loginFailure,
    (state, {error}): AuthState => ({
      ...state,
      loading: false,
      error,
      isAuthenticated: false
    })
  ),

  on(
    AuthActions.loadUserFromTokenSuccess,
    (state, {user, accessToken}): AuthState => ({
      ...state,
      user,
      accessToken,
      isAuthenticated: true,
      loading: false,
      error: null
    })
  )
);
