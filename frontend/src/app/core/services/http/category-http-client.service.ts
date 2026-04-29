import {BaseHttpAbstract} from './base-http.abstract';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ApiResponse} from '../../models/api-response.interface';
import {map} from 'rxjs/operators';
import {CategoryResponse} from '../../models/response/categories/category-response.model';
import {CategoryRequest} from '../../models/request/categories/category-request.model';
import {Category} from '../../models/category.model';

@Injectable({
  providedIn: 'root'
})
export class CategoryHttpClientService extends BaseHttpAbstract {
  protected readonly _SERVICE_PREFIX: string = 'product';

  private readonly _CATEGORIES: string = 'categories';

  constructor(protected _http: HttpClient) {
    super(_http);
  }

  public getAllCategories(): Observable<Category[]> {
    return this.get<ApiResponse<CategoryResponse[]>>(`${this._CATEGORIES}`).pipe(map(apiResponse => apiResponse.data));
  }

  public getById(id: number): Observable<Category> {
    return this.get<ApiResponse<Category>>(`${this._CATEGORIES}/${id}`).pipe(map(resp => resp.data));
  }

  public createCategory(payload: CategoryRequest): Observable<Category> {
    return this.post<ApiResponse<Category>>(`${this._CATEGORIES}`, payload).pipe(map(resp => resp.data));
  }

  public updateCategory(payload: CategoryRequest): Observable<Category> {
    return this.put<ApiResponse<Category>>(`${this._CATEGORIES}/${payload.id}`, payload).pipe(map(resp => resp.data));
  }
}
