import {AfterViewInit, Component, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {CategoryFacade} from './core/state-management/facades/category/category.facade';
import {ProductFacade} from './core/state-management/facades/product/product.facade';
import {AlertService} from './shared/services/alert.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewInit {
  @ViewChild('notificationContainer', {read: ViewContainerRef}) container!: ViewContainerRef;

  constructor(
    private _categoryFacade: CategoryFacade,
    private _productFacade: ProductFacade,
    private _alertService: AlertService
  ) {}

  ngOnInit(): void {
    this._initialize();
  }

  ngAfterViewInit(): void {
    this._initializeAfterView();
  }

  private _initialize(): void {
    this._categoryFacade.loadCategories();
    this._productFacade.loadPage(0);
  }

  private _initializeAfterView(): void {
    this._alertService.registerViewContainer(this.container);
  }
}
