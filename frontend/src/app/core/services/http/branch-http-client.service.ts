import { BaseHttpAbstract } from './base-http.abstract';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../models/api-response.interface';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { RequestParam } from '../../models/request-param.interface';
import { RequestHeader } from '../../models/request-header.interface';
import { Branch } from '../../models/branch.model';

@Injectable({
  providedIn: 'root',
})

export class BranchHttpClientService extends BaseHttpAbstract {
  private readonly _BRANCHES: string = '/branches';
  protected readonly _SERVICE_PREFIX = 'tenant-service';

constructor(protected http: HttpClient) {
    super(http);
  }

  public create(request: Branch): Observable<Branch> {
    return this.post<ApiResponse<Branch>>(
      `${this._BRANCHES}`,
      request
    ).pipe(map((apiResponse) => apiResponse.data));
  }

  public getById(branchId: string, params?: RequestParam, headers?: RequestHeader): Observable<Branch> {
    return this.get<ApiResponse<Branch>>(
      `${this._BRANCHES}/${branchId}`,
      params,
      headers
    ).pipe(map((apiResponse) => apiResponse.data));
  }

  public getAll(params?: RequestParam, headers?: RequestHeader): Observable<Branch[]> {
    return this.get<ApiResponse<Branch[]>>(
      `${this._BRANCHES}`,
      params,
      headers
    ).pipe(map((apiResponse) => apiResponse.data));
  }

  public update(branchId: string, request: Branch): Observable<Branch> {
    return this.put<ApiResponse<Branch>>(
      `${this._BRANCHES}/${branchId}`,
      request
    ).pipe(map((apiResponse) => apiResponse.data));
  }

  public removeById(branchId: string): Observable<Branch> {
    return this.delete<ApiResponse<Branch>>(
      `${this._BRANCHES}/${branchId}`
    ).pipe(map((apiResponse) => apiResponse.data));
  }
}