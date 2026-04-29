import {
  ComponentFactory,
  ComponentFactoryResolver,
  ComponentRef,
  EmbeddedViewRef,
  Injectable,
  Injector,
  ViewContainerRef
} from '@angular/core';
import {AlertTypes} from 'src/app/core/enums/alert-types.enum';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {AlertComponent} from '../components/alert/alert.component';

@Injectable({providedIn: 'root'})
export class AlertService {
  private _alertRefs: Array<ComponentRef<AlertComponent>>;
  private _alertUnsubscribe$: Map<ComponentRef<AlertComponent>, Subject<void>>;
  private container!: ViewContainerRef;

  constructor(private _componentFactoryResolver: ComponentFactoryResolver, private _injector: Injector) {
    this._alertUnsubscribe$ = new Map();
    this._alertRefs = [];
  }

  public registerViewContainer(container: ViewContainerRef) {
    this.container = container;
  }

  public show(message: string, type: AlertTypes = AlertTypes.Info, duration: number = 3000): void {
    const componentFactory: ComponentFactory<AlertComponent> =
      this._componentFactoryResolver.resolveComponentFactory(AlertComponent);
    const componentRef = this.container.createComponent(componentFactory);
    componentRef.instance.message = message;
    componentRef.instance.alertType = type;
    componentRef.instance.duration = duration;
    componentRef.instance.showAlert();

    const unsubscribe$ = new Subject<void>();
    this._alertUnsubscribe$.set(componentRef, unsubscribe$);

    componentRef.instance.appAlertShowChange
      .pipe(takeUntil(unsubscribe$))
      .subscribe(() => this._removeAlert(componentRef));

    this._alertRefs.push(componentRef);
    this._adjustAlertPositions();

    componentRef.changeDetectorRef.markForCheck();
  }

  private _removeAlert(componentRef: ComponentRef<AlertComponent>): void {
    const index: number = this._alertRefs.indexOf(componentRef);

    if (index > -1) {
      this._alertRefs.splice(index, 1);
    }

    const unsubscribe$ = this._alertUnsubscribe$.get(componentRef);
    if (unsubscribe$) {
      unsubscribe$.next();
      unsubscribe$.complete();
      this._alertUnsubscribe$.delete(componentRef);
    }

    componentRef.destroy();

    this._adjustAlertPositions();
  }

  private _adjustAlertPositions(): void {
    this._alertRefs.forEach((ref: ComponentRef<AlertComponent>, index: number) => {
      const domElem: HTMLElement = (ref.hostView as EmbeddedViewRef<AlertComponent>).rootNodes[0] as HTMLElement;
      const alertContainer: HTMLElement | null = domElem.querySelector('.alert-container') as HTMLElement;

      if (alertContainer) {
        alertContainer.style.bottom = `${2 + index * 7}rem`;
      }
    });
  }
}
