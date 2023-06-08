import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {ToastrService} from 'ngx-toastr';
import {ResetPasswordUser} from '../../dtos/user';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.scss']
})
export class PasswordResetComponent {

  passwordResetForm: FormGroup;
  error: string;
  success: string;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private notification: ToastrService
  ) {
    this.passwordResetForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      newPassword: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.passwordResetForm.invalid) {
      return;
    }

    if (this.passwordResetForm.value.confirmPassword !== this.passwordResetForm.value.newPassword) {
      this.error = 'The passwords do not match!';
      return;
    }
    const user: ResetPasswordUser = {
      email: this.passwordResetForm.controls.email.value,
      newPassword: this.passwordResetForm.controls.newPassword.value,
      token: this.getTokenFromUrl()
    };
    console.log('Email:' + user.email);
    console.log('Token:' + user.token);

    const observable = this.userService.resetPassword(user);
    observable.subscribe({
        next: () => {
          this.notification.success('Password successfully reset!');

        },
        error: err => {
          this.notification.error('Failed to reset password.');
        }
      }
    );

  }

  private getTokenFromUrl(): string {
    const url = window.location.href;
    const tokenIndex = url.indexOf('token=');
    if (tokenIndex !== -1) {
      return url.substring(tokenIndex + 6);
    }
    return '';
  }
}
