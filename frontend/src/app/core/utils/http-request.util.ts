import {HttpHeaders, HttpParams} from '@angular/common/http';
import {RequestHeader} from '../models/request-header.interface';
import {RequestParam} from '../models/request-param.interface';

export class HttpRequestUtil {
  public static buildParams(params: RequestParam | undefined): HttpParams {
    let httpParams = new HttpParams();

    if (!params) {
      return httpParams;
    }

    Object.entries(params).forEach(([key, param]) => {
      if (param === undefined || param === null) {
        return;
      }

      if (Array.isArray(param)) {
        param.forEach(value => {
          httpParams = httpParams.append(key, String(value));
        });
      } else if (typeof param === 'boolean' || typeof param === 'number') {
        httpParams = httpParams.append(key, String(param));
      } else {
        httpParams = httpParams.append(key, param);
      }
    });

    return httpParams;
  }

  public static buildHeaders(headers?: RequestHeader, body?: any): HttpHeaders {
    if (body instanceof FormData) {
      let httpHeaders = new HttpHeaders();

      if (!headers) return httpHeaders;

      Object.entries(headers).forEach(([key, value]) => {
        if (value == null) return;
        httpHeaders = httpHeaders.set(key, String(value));
      });

      return httpHeaders;
    }

    let httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

    if (!headers) return httpHeaders;

    Object.entries(headers).forEach(([key, value]) => {
      if (value == null) return;
      httpHeaders = httpHeaders.set(key, String(value));
    });

    return httpHeaders;
  }
}
