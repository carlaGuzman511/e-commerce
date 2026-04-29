import { Injectable, ComponentFactoryResolver, ApplicationRef, Injector, EmbeddedViewRef, ComponentRef, Type } from '@angular/core';
import { Subject } from 'rxjs';

export interface ModalConfig {
  data?: any;
  dismissable?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  private modalComponentRef: ComponentRef<any> | null;

  constructor(
    private componentFactoryResolver: ComponentFactoryResolver,
    private appRef: ApplicationRef,
    private injector: Injector
  ) {
    this.modalComponentRef = null;
  }

  public open<T>(component: Type<T>, config: ModalConfig = {}): Subject<any> {
    this.close();

    const componentRef: ComponentRef<T> = this.componentFactoryResolver
      .resolveComponentFactory(component)
      .create(this.injector);

    if (config.data) {
      Object.assign(componentRef.instance, config.data);
    }

    if (config.dismissable !== undefined) {
      (componentRef.instance as any).dismissable = config.dismissable;
    }

    this.appRef.attachView(componentRef.hostView);

    const domElem: HTMLElement = (componentRef.hostView as EmbeddedViewRef<any>)
      .rootNodes[0] as HTMLElement;

    document.body.appendChild(domElem);

    this.modalComponentRef = componentRef;

    const resultSubject: Subject<any> = new Subject<any>();

    if ((componentRef.instance as any).close) {
      const closeSubscription = (componentRef.instance as any).close.subscribe((result: any) => {
        resultSubject.next(result);
        resultSubject.complete();
        this.close();
        closeSubscription.unsubscribe();
      });
    }

    return resultSubject;
  }

  public close(): void {
    if (this.modalComponentRef) {
      this.appRef.detachView(this.modalComponentRef.hostView);
      this.modalComponentRef.destroy();
      this.modalComponentRef = null;
    }
  }
}
