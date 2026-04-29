import {Injectable} from '@angular/core';
import {CanActivate, Router, UrlTree} from '@angular/router';
import {Observable, of} from 'rxjs';
import {map, switchMap, take} from 'rxjs/operators';
import {AuthFacade} from '../state-management/facades/auth/auth.facade';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private readonly _authFacade: AuthFacade, private readonly _router: Router) {}

  public canActivate(): Observable<boolean | UrlTree> {
    return this._authFacade.selectIsAuthenticated().pipe(
      take(1),
      switchMap(() => {
        return this._authFacade.selectAccessToken().pipe(
          take(1),
          switchMap(token => {
            this._authFacade.loadUserFromToken();
            debugger;
            if (token) {
              return this._authFacade.selectIsAuthenticated().pipe(
                take(1),
                map(restored => (restored ? true : this._router.parseUrl('/kiosko-auth/login')))
              );
            }
            return of(this._router.parseUrl('/kiosko-auth/login'));
          })
        );
      })
    );
  }
}
