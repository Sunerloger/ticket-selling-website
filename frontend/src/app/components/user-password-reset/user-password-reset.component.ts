import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../services/auth.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-user-password-reset',
  templateUrl: './user-password-reset.component.html',
  styleUrls: ['./user-password-reset.component.scss']
})
export class UserPasswordResetComponent {

  passwordResetForm: FormGroup;

  constructor(private userService: UserService,
              private formBuilder: FormBuilder,
              public authService: AuthService,
              private notification: ToastrService) {
    this.passwordResetForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSubmit() {
    if (this.passwordResetForm.invalid) {
      return;
    }

    const email = this.passwordResetForm.controls.email.value;

    //TODO: Change to observable
    this.userService.sendResetMailUser(email).subscribe(() => {
        //success
      },
      (error) => {
        //error
      });
  }

}
