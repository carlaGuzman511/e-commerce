import {BaseHttpAbstract} from './base-http.abstract';
import {Observable} from 'rxjs';
import {LoginRequest} from '../../models/request/auth/login-request.model';
import {ApiResponse} from '../../models/api-response.interface';
import {SimpleAuthResponse} from '../../models/response/auth/simple-auth-response.model';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {RegisterRequest} from '../../models/request/auth/register-request.model';

@Injectable({
  providedIn: 'root'
})
export class AuthHttpClientService extends BaseHttpAbstract {
  protected readonly _SERVICE_PREFIX: string = 'auth';
  private readonly _LOGIN: string = 'auth/login';
  private readonly _REGISTER: string = 'register';
  private readonly _REFRESH_TOKEN: string = 'refresh-token';

  constructor(protected _http: HttpClient) {
    super(_http);
  }

  public login(request: LoginRequest): Observable<SimpleAuthResponse> {
    return this.post<ApiResponse<SimpleAuthResponse>>(`${this._LOGIN}`, request).pipe(
      map(apiResponse => apiResponse.data)
    );
  }

  public register(request: RegisterRequest): Observable<SimpleAuthResponse> {
    return this.post<ApiResponse<SimpleAuthResponse>>(`${this._REGISTER}`, request).pipe(
      map(apiResponse => apiResponse.data)
    );
  }

  public refreshToken(refreshToken: string): Observable<SimpleAuthResponse> {
    return this.post<ApiResponse<SimpleAuthResponse>>(`${this._SERVICE_PREFIX}/${this._REFRESH_TOKEN}`, {
      refreshToken
    }).pipe(map(apiResponse => apiResponse.data));
  }
}
