import { BaseHttpAbstract } from './base-http.abstract';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../models/api-response.interface';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Store } from '../../models/store.model';
import { RequestParam } from '../../models/request-param.interface';
import { RequestHeader } from '../../models/request-header.interface';
@Injectable({
  providedIn: 'root',
})
export class StoreHttpClientService extends BaseHttpAbstract {
  private readonly _TENANT_URL: string = 'stores';
  protected readonly _SERVICE_PREFIX = 'tenant-service';
  
  constructor(protected http: HttpClient) {
    super(http);
  }

  public create(request: Store): Observable<Store> {
    return this.post<ApiResponse<Store>>(
      `${this._TENANT_URL}`,
      request
    ).pipe(map((apiResponse) => apiResponse.data));
  }

  public getById(id: string, params?: RequestParam, headers?: RequestHeader): Observable<Store> {
    return this.get<ApiResponse<Store>>(
      `${this._TENANT_URL}/${id}`,
      params,
      headers
    ).pipe(map((apiResponse) => apiResponse.data));
  }

  public getAll(params?: RequestParam, headers?: RequestHeader): Observable<Store[]> {
    return this.get<ApiResponse<Store[]>>(
      `${this._TENANT_URL}`,
      params,
      headers
    ).pipe(map((apiResponse) => apiResponse.data));
  }

  public update(request: Store): Observable<Store> {
    return this.put<ApiResponse<Store>>(
      `${this._TENANT_URL}/${request.id}`,
      request
    ).pipe(map((apiResponse) => apiResponse.data));
  }

  public removeById(id: string): Observable<Store> {
    return this.delete<ApiResponse<Store>>(
      `${this._TENANT_URL}/${id}`
    ).pipe(map((apiResponse) => apiResponse.data));
  }
}