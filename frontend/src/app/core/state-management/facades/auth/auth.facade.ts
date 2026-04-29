import {Store} from '@ngrx/store';
import {AppState} from '../../app.state';
import {LoginRequest} from '../../../models/request/auth/login-request.model';
import * as AuthActions from '../../actions/auth/auth.action';
import {login} from '../../actions/auth/auth.action';
import {Injectable} from '@angular/core';
import {
  selectAuthAccessToken,
  selectAuthLoading,
  selectAuthUser,
  selectAuthUserRoles,
  selectIsAuthenticated
} from '../../selectors/auth/auth.selectors';
import {SimpleUserResponse} from '../../../models/response/auth/simple-user-response.model';
import {Observable} from 'rxjs';

@Injectable({providedIn: 'root'})
export class AuthFacade {
  constructor(private _store: Store<AppState>) {}

  public login(loginRequest: LoginRequest): void {
    this._store.dispatch(login({credentials: loginRequest}));
  }

  public selectUser(): Observable<SimpleUserResponse | null> {
    return this._store.select(selectAuthUser);
  }

  public selectAccessToken(): Observable<string | null> {
    return this._store.select(selectAuthAccessToken);
  }

  public selectIsAuthenticated(): Observable<boolean> {
    return this._store.select(selectIsAuthenticated);
  }

  public selectIsLoading(): Observable<boolean> {
    return this._store.select(selectAuthLoading);
  }

  public selectRoles(): Observable<string[]> {
    return this._store.select(selectAuthUserRoles);
  }

  public loadUserFromToken(): void {
    this._store.dispatch(AuthActions.loadUserFromToken());
  }

  public refreshToken(token: string): void {
    this._store.dispatch(AuthActions.refreshToken({token}));
  }
}
