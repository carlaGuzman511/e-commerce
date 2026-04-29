import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnDestroy,
  Output
} from '@angular/core';
import {AlertTypes} from 'src/app/core/enums/alert-types.enum';
import {Subject, timer} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {EMPTY} from '../../constants/app-const.constant';
import {ALERT_ICON_MAP} from '../../constants/alert.constants';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AlertComponent implements OnDestroy {
  @Input() public alertType: AlertTypes;
  @Input() public duration: number;
  @Input() public message: string;

  @Output() public appAlertShowChange: EventEmitter<boolean>;

  public iconClass: string;
  public isShow: boolean;
  public title: string;

  private _cancelTimer$: Subject<void>;

  private readonly _unsubscribe$: Subject<void>;

  constructor(private _changeDetectorRef: ChangeDetectorRef) {
    this.alertType = AlertTypes.Info;
    this._cancelTimer$ = new Subject<void>();
    this._unsubscribe$ = new Subject<void>();
    this.appAlertShowChange = new EventEmitter<boolean>();
    this.duration = 2000;
    this.iconClass = 'icon-note-filled';
    this.message = EMPTY;
    this.isShow = false;
    this.title = 'Info';

    this._cancelTimer$ = new Subject<void>();
    this._unsubscribe$ = new Subject<void>();
  }

  ngOnDestroy(): void {
    this._finalize();
  }

  public showAlert(): void {
    this.isShow = true;
    this.title = this.alertType;
    this._setIcon();
    this._startTimer();
    this._changeDetectorRef.markForCheck();
  }

  public close(): void {
    this.isShow = false;
    this.appAlertShowChange.emit(false);
  }

  public getAlertClass(): string {
    return `alert-${this.alertType.toLowerCase()}`;
  }

  private _finalize(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
    this._cancelTimer$.complete();
  }

  private _setIcon(): void {
    this.iconClass = ALERT_ICON_MAP[this.alertType] || 'icon-note-filled';
  }

  private _startTimer(): void {
    this._cancelTimer$.next();

    if (this.duration > 0) {
      timer(this.duration)
        .pipe(takeUntil(this._cancelTimer$), takeUntil(this._unsubscribe$))
        .subscribe(() => {
          this.close();
          this._changeDetectorRef.markForCheck();
        });
    }
  }
}
