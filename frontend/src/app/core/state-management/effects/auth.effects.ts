import {Actions, createEffect, ofType} from '@ngrx/effects';
import {Router} from '@angular/router';
import * as AuthActions from '../actions/auth/auth.action';
import {catchError, exhaustMap, map, tap} from 'rxjs/operators';
import {TokenService} from '../../services/token.service';
import {AuthHttpClientService} from '../../services/http/auth-http-client.service';
import {of} from 'rxjs';
import {Injectable} from '@angular/core';
import {SessionService} from '../../services/session.service';
import {SimpleAuthResponse} from '../../models/response/auth/simple-auth-response.model';

@Injectable()
export class AuthEffects {
  login$ = createEffect(() =>
    this._actions.pipe(
      ofType(AuthActions.login),
      exhaustMap(action =>
        this._authService.login(action.credentials).pipe(
          map(response => {
            this._tokenService.setRefreshToken(response.refreshToken);
            return AuthActions.loginSuccess({authResponse: response});
          }),
          catchError(error => of(AuthActions.loginFailure({error})))
        )
      )
    )
  );

  loginSuccess$ = createEffect(
    () =>
      this._actions.pipe(
        ofType(AuthActions.loginSuccess),
        tap(() => {
          this._router.navigate(['/dashboard']);
        })
      ),
    {dispatch: false}
  );

  LoadUserFromToken$ = createEffect(() =>
    this._actions.pipe(
      ofType(AuthActions.loadUserFromToken, AuthActions.refreshToken),
      exhaustMap(() => {
        const refreshToken: string | null = this._tokenService.getRefreshToken();
        console.log('Refresh token:', refreshToken);

        if (!refreshToken) {
          return of(AuthActions.loadUserFromTokenFailure());
        }

        return this._authService.refreshToken(refreshToken).pipe(
          map((user: SimpleAuthResponse) =>
            AuthActions.loadUserFromTokenSuccess({
              user: user.user,
              accessToken: user.accessToken
            })
          ),
          catchError(() => of(AuthActions.loadUserFromTokenFailure()))
        );
      })
    )
  );

  constructor(
    private _authService: AuthHttpClientService,
    private _sessionService: SessionService,
    private _tokenService: TokenService,
    private _actions: Actions,
    private _router: Router
  ) {}
}
