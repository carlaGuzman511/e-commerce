import {Injectable} from '@angular/core';
import {SimpleUserResponse} from '../models/response/auth/simple-user-response.model';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private readonly _ACCESS_TOKEN_KEY = 'access_token';
  private readonly _REFRESH_TOKEN_KEY = 'refresh_token';
  private readonly _USER_INFO_KEY = 'user_info';
  private readonly _TOKEN_EXPIRY_KEY = 'token_expiry';

  constructor() {}

  public saveTokens(accessToken: string, refreshToken: string, expiresIn: number, rememberMe: boolean = false): void {
    sessionStorage.setItem(this._ACCESS_TOKEN_KEY, accessToken);

    const expiryTime: number = new Date().getTime() + expiresIn;
    sessionStorage.setItem(this._TOKEN_EXPIRY_KEY, expiryTime.toString());

    if (rememberMe) {
      localStorage.setItem(this._REFRESH_TOKEN_KEY, refreshToken);
    } else {
      sessionStorage.setItem(this._REFRESH_TOKEN_KEY, refreshToken);
    }
  }

  public saveRefreshToken(refreshToken: string): void {
    localStorage.setItem(this._REFRESH_TOKEN_KEY, refreshToken);
  }

  public saveUserInfo(user: SimpleUserResponse): void {
    const minimalUser = {
      id: user.id,
      email: user.email,
      roles: user.roles,
      tenantId: user.tenantId
    };

    sessionStorage.setItem(this._USER_INFO_KEY, JSON.stringify(minimalUser));
  }

  public getAccessToken(): string | null {
    return sessionStorage.getItem(this._ACCESS_TOKEN_KEY);
  }

  public getRefreshToken(): string | null {
    return sessionStorage.getItem(this._REFRESH_TOKEN_KEY) || localStorage.getItem(this._REFRESH_TOKEN_KEY);
  }

  public getUserInfo(): Partial<SimpleUserResponse> | null {
    const userJson: string | null = sessionStorage.getItem(this._USER_INFO_KEY);
    return userJson ? JSON.parse(userJson) : null;
  }

  public isAuthenticated(): boolean {
    const token: string | null = this.getAccessToken();
    return token !== null && !this._isTokenExpired();
  }

  public clearTokens(): void {
    sessionStorage.removeItem(this._ACCESS_TOKEN_KEY);
    sessionStorage.removeItem(this._REFRESH_TOKEN_KEY);
    sessionStorage.removeItem(this._USER_INFO_KEY);
    sessionStorage.removeItem(this._TOKEN_EXPIRY_KEY);

    localStorage.removeItem(this._REFRESH_TOKEN_KEY);
  }

  public updateAccessToken(accessToken: string, expiresIn: number): void {
    sessionStorage.setItem(this._ACCESS_TOKEN_KEY, accessToken);

    const expiryTime: number = new Date().getTime() + expiresIn;
    sessionStorage.setItem(this._TOKEN_EXPIRY_KEY, expiryTime.toString());
  }

  public isTokenExpiringSoon(thresholdMinutes: number = 2): boolean {
    const expiryTime: string | null = sessionStorage.getItem(this._TOKEN_EXPIRY_KEY);

    if (!expiryTime) {
      return true;
    }

    const now: number = new Date().getTime();
    const expiry: number = parseInt(expiryTime, 10);
    const thresholdMs: number = thresholdMinutes * 60 * 1000;

    return expiry - now <= thresholdMs;
  }

  public getTokenTimeRemaining(): number {
    const expiryTime: string | null = sessionStorage.getItem(this._TOKEN_EXPIRY_KEY);

    if (!expiryTime) {
      return 0;
    }

    const now: number = new Date().getTime();
    const expiry: number = parseInt(expiryTime, 10);
    const remaining: number = expiry - now;

    return remaining > 0 ? remaining : 0;
  }

  private _isTokenExpired(): boolean {
    const expiryTime: string | null = sessionStorage.getItem(this._TOKEN_EXPIRY_KEY);

    if (!expiryTime) {
      return true;
    }

    const now: number = new Date().getTime();
    return now >= parseInt(expiryTime, 10);
  }
}
