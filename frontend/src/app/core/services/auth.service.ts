import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {BehaviorSubject, Observable, throwError} from 'rxjs';
import {catchError, map, tap} from 'rxjs/operators';
import {SimpleUserResponse} from '../models/response/auth/simple-user-response.model';
import {AuthHttpClientService} from './http/auth-http-client.service';
import {SessionService} from './session.service';
import {LoginRequest} from '../models/request/auth/login-request.model';
import {UserRole} from '../enums/user-role.enum';
import {RegisterRequest} from '../models/request/auth/register-request.model';

@Injectable()
export class AuthService {
  public currentUser$: Observable<SimpleUserResponse | null>;

  private _currentUserSubject: BehaviorSubject<SimpleUserResponse | null>;

  constructor(
    private _authHttpService: AuthHttpClientService,
    private _sessionService: SessionService,
    private _router: Router
  ) {
    const storedUser = this._sessionService.getUserInfo();
    this._currentUserSubject = new BehaviorSubject<SimpleUserResponse | null>(storedUser as SimpleUserResponse);
    this.currentUser$ = this._currentUserSubject.asObservable();
  }

  public get currentUserValue(): SimpleUserResponse | null {
    return this._currentUserSubject.value;
  }

  public login(credentials: LoginRequest, rememberMe: boolean = false): Observable<SimpleUserResponse> {
    return this._authHttpService.login(credentials).pipe(
      tap(response => {
        const {accessToken, refreshToken, expiresIn, user} = response;

        this._sessionService.saveTokens(accessToken, refreshToken, expiresIn, rememberMe);
        this._sessionService.saveUserInfo(user);
        this._currentUserSubject.next(user);
      }),
      map(response => response.user),
      catchError(error => {
        return throwError(() => error);
      })
    );
  }

  public register(credentials: RegisterRequest, rememberMe: boolean = false): Observable<SimpleUserResponse> {
    return this._authHttpService.register(credentials).pipe(
      tap(response => {
        const {accessToken, refreshToken, expiresIn, user} = response;

        this._sessionService.saveTokens(accessToken, refreshToken, expiresIn, rememberMe);
        this._sessionService.saveUserInfo(user);
        this._currentUserSubject.next(user);
      }),
      map(response => response.user),
      catchError(error => {
        return throwError(() => error);
      })
    );
  }

  public logout(): void {
    this._sessionService.clearTokens();
    this._currentUserSubject.next(null);
    this._router.navigate(['/public/auth/login']);
  }

  public refreshToken(): Observable<string> {
    const refreshToken = this._sessionService.getRefreshToken();

    if (!refreshToken) {
      this.logout();
      return throwError(() => new Error('No refresh token available'));
    }

    return this._authHttpService.refreshToken(refreshToken).pipe(
      tap(response => {
        const {accessToken, refreshToken: newRefreshToken, expiresIn, user} = response;

        const rememberMe = !!localStorage.getItem('refresh_token');

        this._sessionService.saveTokens(accessToken, newRefreshToken, expiresIn, rememberMe);
        this._sessionService.saveUserInfo(user);
        this._currentUserSubject.next(user);
      }),
      map(response => response.accessToken),
      catchError(error => {
        this.logout();
        return throwError(() => error);
      })
    );
  }

  public isAuthenticated(): boolean {
    return this._sessionService.isAuthenticated();
  }

  public hasRole(role: UserRole): boolean {
    const user = this.currentUserValue;
    return user?.roles.includes(role) || false;
  }

  public hasAnyRole(roles: UserRole[]): boolean {
    const user = this.currentUserValue;
    if (!user) {
      return false;
    }
    return roles.some(role => user.roles.includes(role));
  }

  public getAccessToken(): string | null {
    return this._sessionService.getAccessToken();
  }

  public getRefreshToken(): string | null {
    return this._sessionService.getRefreshToken();
  }
}
