import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {BaseHttpAbstract} from './base-http.abstract';
import {ApiResponse} from '../../models/api-response.interface';
import {MediaResponse} from '../../models/response/media/media-response.model';
import {map} from 'rxjs/operators';
import {MediaRequest} from '../../models/request/media/media-request.model';
import {OwnerType} from '../../enums/owner-type.enum';

@Injectable({
  providedIn: 'root'
})
export class MediaHttpClientService extends BaseHttpAbstract {
  protected readonly _SERVICE_PREFIX: string = 'media';

  private readonly _UPLOAD = 'media/upload';
  private readonly _BATCH = '_batch';

  constructor(protected override _http: HttpClient) {
    super(_http);
  }

  public upload(mediaRequest: MediaRequest): Observable<MediaResponse> {
    const form = new FormData();
    form.append('file', mediaRequest.file);
    form.append('folder', mediaRequest.folder);
    form.append('ownerType', mediaRequest.ownerType);
    if (mediaRequest.orderIndex !== undefined) {
      form.append('orderIndex', mediaRequest.orderIndex.toString());
    }

    return this.post<ApiResponse<MediaResponse>>(this._UPLOAD, form).pipe(map(response => response.data));
  }

  public list(ownerType?: OwnerType): Observable<MediaResponse[]> {
    return this.get<ApiResponse<MediaResponse[]>>('/', {ownerType}).pipe(map(response => response.data));
  }

  public deleteMedia(publicId: string): Observable<void> {
    return this.delete<ApiResponse<void>>('/', {publicId}).pipe(map(response => response.data));
  }

  public getByPublicIds(publicIds: string[]): Observable<MediaResponse[]> {
    const body = {publicIds};
    return this.post<ApiResponse<MediaResponse[]>>(`${this._SERVICE_PREFIX}/${this._BATCH}`, body).pipe(
      map(response => response.data)
    );
  }
}
