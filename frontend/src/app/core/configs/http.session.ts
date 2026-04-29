import {
  REFRESH_TOKEN_IS_NOT_DEFINED,
  TENANT_ID_IS_NOT_DEFINED,
  TOKEN_IS_NOT_DEFINED,
  USER_ID_IS_NOT_DEFINED,
} from '../constants/http-session.const';

export class HttpSession {
  private readonly _TENANT_ID: string = 'TENANT_ID';
  private readonly _TOKEN: string = 'TOKEN';
  private readonly _REFRESH_TOKEN: string = 'REFRESH_TOKEN';
  private readonly _USER_ID: string = 'USER_ID';

  constructor() {}

  public open(userId: string, tenantId: string, token: string): void {
    sessionStorage.setItem(this._USER_ID, userId);
    sessionStorage.setItem(this._TENANT_ID, tenantId);
    sessionStorage.setItem(this._TOKEN, token);
  }

  public getUserId(): string {
    const userId = sessionStorage.getItem(this._USER_ID);
    if (!userId) {
      throw new Error(USER_ID_IS_NOT_DEFINED);
    }
    return userId;
  }

  public getTenantId(): string {
    const tenantId = sessionStorage.getItem(this._TENANT_ID);
    if (!tenantId) {
      throw new Error(TENANT_ID_IS_NOT_DEFINED);
    }
    return tenantId;
  }

  public getToken(): string {
    const token = sessionStorage.getItem(this._TOKEN);
    if (!token) {
      throw new Error(TOKEN_IS_NOT_DEFINED);
    }
    return token;
  }

  public getRefreshToken(): string {
    const refreshToken = sessionStorage.getItem(this._REFRESH_TOKEN);
    if (!refreshToken) {
      throw new Error(REFRESH_TOKEN_IS_NOT_DEFINED);
    }
    return refreshToken;
  }

  public setTenantId(tenantId: string): void {
    sessionStorage.setItem(this._TENANT_ID, tenantId);
  }

  public setPrefix(key: string, value: string): void {
    sessionStorage.setItem(key, value);
  }

  public close(): void {
    sessionStorage.removeItem(this._USER_ID);
    sessionStorage.removeItem(this._TENANT_ID);
    sessionStorage.removeItem(this._TOKEN);
  }
}
