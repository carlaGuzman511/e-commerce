import {Injectable} from '@angular/core';
import {Observable, ReplaySubject} from 'rxjs';

@Injectable({providedIn: 'root'})
export class TenantService {
  private _tenant: ReplaySubject<string>;

  constructor() {
    this._tenant = new ReplaySubject<string>(1);
  }

  public value(): Observable<string> {
    return this._tenant.asObservable();
  }

  public set(value: string): void {
    this._tenant.next(value);
  }
}
