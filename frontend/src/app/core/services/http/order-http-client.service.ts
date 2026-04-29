import { BaseHttpAbstract } from './base-http.abstract';
import { Observable } from 'rxjs';
import { ApiResponse } from '../../models/api-response.interface';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Order } from '../../models/order.model';
import { RequestParam } from 'src/app/core/models/request-param.interface';
import { RequestHeader } from 'src/app/core/models/request-header.interface';
@Injectable({
  providedIn: 'root',
})
export class OrderHttpClientService extends BaseHttpAbstract {
  private readonly _ORDERS_URL: string = 'orders';
  protected readonly _SERVICE_PREFIX: string = 'order-service';
  
  constructor(protected http: HttpClient) {
    super(http);
  }

  public create(request: Order): Observable<Order> {
    return this.post<ApiResponse<Order>>(
      `${this._ORDERS_URL}`,
      request
    ).pipe(map((apiResponse) => apiResponse.data));
  }

  public getById(id: string, params?: RequestParam, headers?: RequestHeader): Observable<Order> {
    return this.get<ApiResponse<Order>>(
      `${this._ORDERS_URL}/${id}`,
      params,
      headers
    ).pipe(map((apiResponse) => apiResponse.data));
  }

  public getAll(params?: RequestParam, headers?: RequestHeader): Observable<Order[]> {
    return this.get<ApiResponse<Order[]>>(
      `${this._ORDERS_URL}`,
      params,
      headers
    ).pipe(map((apiResponse) => apiResponse.data));
  }

  public update(request: Order): Observable<Order> {
    return this.put<ApiResponse<Order>>(
      `${this._ORDERS_URL}/${request.id}`,
      request
    ).pipe(map((apiResponse) => apiResponse.data));
  }

  public removeById(id: string): Observable<Order> {
    return this.delete<ApiResponse<Order>>(
      `${this._ORDERS_URL}/${id}`
    ).pipe(map((apiResponse) => apiResponse.data));
  }
}