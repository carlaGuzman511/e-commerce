import {BaseHttpAbstract} from './base-http.abstract';
import {Observable} from 'rxjs';
import {ApiResponse} from '../../models/api-response.interface';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {ProductCreateRequest} from '../../models/request/product/product-create-request.model';
import {ProductResponse} from '../../models/response/product/product-response.model';
import {PageResponse} from '../../models/response/page-response';

@Injectable({
  providedIn: 'root'
})
export class ProductHttpClientService extends BaseHttpAbstract {
  protected readonly _SERVICE_PREFIX: string = 'product';

  private readonly _ID: string = 'id';
  private readonly _PRODUCTS: string = 'products';

  constructor(protected _http: HttpClient) {
    super(_http);
  }

  public createProduct(request: ProductCreateRequest): Observable<ProductResponse> {
    return this.post<ApiResponse<ProductResponse>>(`${this._PRODUCTS}`, request).pipe(
      map(apiResponse => apiResponse.data)
    );
  }

  public getActiveProductsWithStock(page: number = 0, size: number = 10): Observable<PageResponse<ProductResponse>> {
    return this.get<ApiResponse<PageResponse<ProductResponse>>>(
      `${this._PRODUCTS}/with-stock?page=${page}&size=${size}`
    ).pipe(map(apiResponse => apiResponse.data));
  }

  public getById(id: string): Observable<ProductResponse> {
    return this.get<ApiResponse<ProductResponse>>(`${this._PRODUCTS}/${this._ID}/${id}`).pipe(
      map(apiResponse => apiResponse.data)
    );
  }
}
