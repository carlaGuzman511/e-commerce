import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Injectable} from '@angular/core';
import {TenantService} from './tenant.service';

@Injectable({providedIn: 'root'})
export class TenantResolver implements Resolve<boolean> {
  constructor(private _tenantService: TenantService) {}

  public resolve(route: ActivatedRouteSnapshot): boolean {
    const tenant = route.paramMap.get('tenant');

    if (!tenant) {
      return true;
    }

    this._tenantService.set(tenant);
    return true;
  }
}
