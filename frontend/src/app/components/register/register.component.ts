import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../../services/user.service';
import {User} from '../../dtos/user';
import {ToastrService} from 'ngx-toastr';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  user: User = {
    admin: false,
    email: '',
    firstName: '',
    lastName: '',
    birthdate: new Date(),
    address: '',
    areaCode: null,
    cityName: '',
    password: '',

  };
  registerForm: FormGroup;
  submitted = false;
  //Error flag
  error = false;
  errorMessage = '';

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              public authService: AuthService,
              private router: Router) {

  }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      admin: [''],
      email: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      birthdate: ['', Validators.required],
      address: ['', Validators.required],
      areaCode: ['', Validators.required],
      cityName: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  registerUser(): void {
    this.registerForm.controls['admin'].setValue(this.user.admin);
    this.registerForm.controls['email'].setValue(this.user.email);
    this.registerForm.controls['firstName'].setValue(this.user.firstName);
    this.registerForm.controls['lastName'].setValue(this.user.lastName);
    this.registerForm.controls['birthdate'].setValue(this.user.birthdate);
    this.registerForm.controls['address'].setValue(this.user.address);
    this.registerForm.controls['areaCode'].setValue(this.user.areaCode);
    this.registerForm.controls['cityName'].setValue(this.user.cityName);
    this.registerForm.controls['password'].setValue(this.user.password);

    console.log(this.registerForm);
    if (this.registerForm.valid) {
      console.log(this.user);
      const observable = this.userService.registerUser(this.user);
      observable.subscribe({
        next: () => {
          this.router.navigate(['/login']);
        },
        error: err => {
          console.error(`Error registering user`, err, this.user);
          //this.notification.error('Could not register user. Errorcode: ' + err.status + ', Errortext: ' + err.error.errors);
        }
      });
    } else {
      this.registerForm.markAllAsTouched();
    }


  }


}
