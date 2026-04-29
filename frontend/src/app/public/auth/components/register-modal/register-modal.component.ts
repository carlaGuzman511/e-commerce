import {Component, EventEmitter, HostListener, OnDestroy, OnInit, Output} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {RegisterRequest} from '../../../../core/models/request/auth/register-request.model';

@Component({
  selector: 'register-modal',
  templateUrl: './register-modal.component.html',
  styleUrls: ['./register-modal.component.scss']
})
export class RegisterModalComponent implements OnInit, OnDestroy {
  @Output()
  public close: EventEmitter<RegisterRequest | null>;

  public registerForm: FormGroup;
  public dismissable: boolean;
  public isSubmitting: boolean;

  constructor(private _formBuilder: FormBuilder) {
    this.close = new EventEmitter<RegisterRequest | null>();
    this.dismissable = true;
    this.isSubmitting = false;

    this.registerForm = this._formBuilder.group(
      {
        username: ['', [Validators.required, Validators.minLength(3)]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(8)]],
        confirmPassword: ['', [Validators.required]]
      },
      {
        validators: this.passwordMatchValidator
      }
    );
  }

  public ngOnInit(): void {
    document.body.style.overflow = 'hidden';
  }

  public ngOnDestroy(): void {
    document.body.style.overflow = 'auto';
  }

  private passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password: string = control.get('password')?.value;
    const confirmPassword: string = control.get('confirmPassword')?.value;

    if (!password || !confirmPassword) {
      return null;
    }

    return password === confirmPassword ? null : {passwordMismatch: true};
  }

  @HostListener('document:keydown.escape', ['$event'])
  public onEscapeKey(event: KeyboardEvent): void {
    if (this.dismissable && !this.isSubmitting) {
      this.onCancel();
    }
  }

  public onBackdropClick(event: MouseEvent): void {
    if (this.dismissable && !this.isSubmitting && event.target === event.currentTarget) {
      this.onCancel();
    }
  }

  public onSubmit(): void {
    if (this.registerForm.valid && !this.isSubmitting) {
      this.isSubmitting = true;

      const userData: RegisterRequest = {
        username: this.registerForm.value.username,
        email: this.registerForm.value.email,
        password: this.registerForm.value.password
      };

      this.close.emit(userData);
    } else {
      Object.keys(this.registerForm.controls).map((key: string) => {
        this.registerForm.get(key)?.markAsTouched();
      });
    }
  }

  public onCancel(): void {
    if (!this.isSubmitting) {
      this.close.emit(null);
    }
  }

  public hasError(controlName: string, errorType: string): boolean {
    const control: AbstractControl | null = this.registerForm.get(controlName);
    return !!(control && control.hasError(errorType) && (control.dirty || control.touched));
  }

  public get passwordMismatch(): boolean {
    return !!(this.registerForm.hasError('passwordMismatch') && this.registerForm.get('confirmPassword')?.touched);
  }
}
