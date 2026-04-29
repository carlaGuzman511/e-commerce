import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {TenantService} from '../services/tenant.service';
import {Observable} from 'rxjs';
import {switchMap, take} from 'rxjs/operators';

@Injectable()
export class TenantInterceptor implements HttpInterceptor {
  constructor(private _tenantService: TenantService) {}

  public intercept<T>(req: HttpRequest<T>, next: HttpHandler): Observable<HttpEvent<T>> {
    const skipTenant =
      req.url.startsWith('/frontend-auth') || req.url.startsWith('/auth-angular') || req.url.startsWith('/api/auth');

    if (skipTenant) {
      return next.handle(req);
    }
    return this._tenantService.value().pipe(
      take(1),
      switchMap(tenant => {
        const modifiedReq = req.clone({
          setHeaders: {
            'X-Tenant-Id': tenant ?? ''
          }
        });
        return next.handle(modifiedReq);
      })
    );
  }
}
