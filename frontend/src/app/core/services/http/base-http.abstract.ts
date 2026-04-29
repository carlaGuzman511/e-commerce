import { HttpClient } from '@angular/common/http';
import { RequestParam } from '../../models/request-param.interface';
import { RequestHeader } from '../../models/request-header.interface';
import { Observable, throwError } from 'rxjs';
import { HttpRequestUtil } from '../../utils/http-request.util';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

export abstract class BaseHttpAbstract {
  protected abstract readonly _SERVICE_PREFIX: string;
  protected readonly _apiVersion: string;
  protected readonly baseUrl: string;

  constructor(protected _http: HttpClient) {
    this._apiVersion = 'api/v1/';
    this.baseUrl = `${environment.API}`;
  }

  private _buildUrl(endpoint: string): string {
    return `${this.baseUrl}/${this._SERVICE_PREFIX}/${this._apiVersion}${endpoint}`;
  }

  public get<T>(endpoint: string, params?: RequestParam, headers?: RequestHeader): Observable<T> {
    return this._http
      .get<T>(this._buildUrl(endpoint), {
        params: HttpRequestUtil.buildParams(params),
        headers: HttpRequestUtil.buildHeaders(headers)
      })
      .pipe(catchError(this._handleError));
  }

  public post<T>(endpoint: string, body?: any, headers?: RequestHeader): Observable<T> {
    return this._http
      .post<T>(this._buildUrl(endpoint), body, {
        headers: HttpRequestUtil.buildHeaders(headers, body)
      })
      .pipe(catchError(this._handleError));
  }

  public put<T>(endpoint: string, body?: Object, headers?: RequestHeader): Observable<T> {
    return this._http
      .put<T>(this._buildUrl(endpoint), body, {
        headers: HttpRequestUtil.buildHeaders(headers)
      })
      .pipe(catchError(this._handleError));
  }

  public delete<T>(endpoint: string, params?: RequestParam, headers?: RequestHeader): Observable<T> {
    return this._http
      .delete<T>(this._buildUrl(endpoint), {
        params: HttpRequestUtil.buildParams(params),
        headers: HttpRequestUtil.buildHeaders(headers)
      })
      .pipe(catchError(this._handleError));
  }

  private _handleError(error: any): Observable<never> {
    const status = error?.status ?? 0;
    const message = error?.error?.message || error?.message;

    console.error(`[HTTP ERROR ${status}]`, message);
    return throwError(() => ({
      status,
      message,
      details: error?.error ?? null
    }));
  }
}
