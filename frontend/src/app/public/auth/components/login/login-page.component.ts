import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {ModalService} from '../../../../shared/services/modal.service';
import {Subject} from 'rxjs';
import {RegisterModalComponent} from '../register-modal/register-modal.component';
import {RegisterRequest} from '../../../../core/models/request/auth/register-request.model';
import {AuthFacade} from '../../../../core/state-management/facades/auth/auth.facade';

@Component({
  selector: 'login-page',
  templateUrl: 'login-page.component.html',
  styleUrls: ['login-page.component.scss']
})
export class LoginPageComponent {
  public loginForm: FormGroup;
  private _returnUrl: string;

  constructor(
    private _modalService: ModalService,
    private _formBuilder: FormBuilder,
    private _authFacade: AuthFacade,
    private _router: Router
  ) {
    this.loginForm = this._formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
    this._returnUrl = '/secure/dashboard';
  }

  public get email() {
    return this.loginForm.get('email');
  }

  public get password() {
    return this.loginForm.get('password');
  }

  public openRegisterModal(): void {
    const modalRef: Subject<any> = this._modalService.open(RegisterModalComponent, {
      dismissable: true
    });

    modalRef.subscribe(
      (result: RegisterRequest | null): void => {
        if (result) {
          this.handleUserRegistration(result);
        }
      },
      (error: any): void => {
        console.error('Modal Error:', error);
      }
    );
  }

  private handleUserRegistration(userData: RegisterRequest): void {
    // this._authFacade.register(userData);
    // this._authService.register(userData).subscribe({
    //   next: (): void => {
    //     this._router.navigate([this._returnUrl]);
    //   },
    //   error: (error): void => {
    //     console.error(error);
    //   }
    // });
  }

  public onSubmit(): void {
    if (this.loginForm.valid) {
      this._authFacade.login(this.loginForm.value);
      //   .subscribe({
      //   next: (data: SimpleUserResponse): void => {
      //     console.warn(data);
      //     this._router.navigate([this._returnUrl]);
      //   },
      //   error: (error): void => {
      //     console.error(error);
      //   }
      // });
    }
  }
}
