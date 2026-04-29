import {createAction, props} from '@ngrx/store';
import {LoginRequest} from '../../../models/request/auth/login-request.model';
import {SimpleAuthResponse} from '../../../models/response/auth/simple-auth-response.model';
import {SimpleUserResponse} from '../../../models/response/auth/simple-user-response.model';

export enum LoginAction {
  Login = '[Auth] Login user',
  LoginSuccess = '[Auth] Login success',
  LoadUserFromToken = '[Auth] Login token',
  RefreshToken = '[Auth] Login token',
  LoadUserFromTokenFailure = '[Auth] Login load user from token failure',
  LoadUserFromTokenSuccess = '[Auth] Login load token from token failure',
  LoginFailure = '[Auth] Login success'
}

export const login = createAction(LoginAction.Login, props<{credentials: LoginRequest}>());
export const loginSuccess = createAction(LoginAction.LoginSuccess, props<{authResponse: SimpleAuthResponse}>());
export const loginFailure = createAction(LoginAction.LoginFailure, props<{error: any}>());
export const loadUserFromToken = createAction(LoginAction.LoadUserFromToken);
export const loadUserFromTokenFailure = createAction(LoginAction.LoadUserFromTokenFailure);
export const loadUserFromTokenSuccess = createAction(
  LoginAction.LoadUserFromTokenSuccess,
  props<{
    user: SimpleUserResponse;
    accessToken: string;
  }>()
);
export const refreshToken = createAction(LoginAction.RefreshToken, props<{token: string}>());
