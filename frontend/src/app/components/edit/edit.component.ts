import {Component, OnInit} from '@angular/core';
import {User} from '../../dtos/user';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../services/auth.service';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss']
})
export class EditComponent implements OnInit {
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

  editForm: FormGroup;
  submitted = false;
  //Error flag
  error = false;
  errorMessage = '';

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              public authService: AuthService,
              private notification: ToastrService,
              private router: Router) {

  }


  ngOnInit(): void {
    console.log(this.user);
    this.userService.getUser(this.authService.getToken()).subscribe(user => {
      this.user = user;
      this.user.password = '';
      console.log('User ', user);
    });
    this.editForm = this.formBuilder.group({
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

  editUser() {
    this.editForm.controls['admin'].setValue(this.user.admin);
    this.editForm.controls['email'].setValue(this.user.email);
    this.editForm.controls['firstName'].setValue(this.user.firstName);
    this.editForm.controls['lastName'].setValue(this.user.lastName);
    this.editForm.controls['birthdate'].setValue(this.user.birthdate);
    this.editForm.controls['address'].setValue(this.user.address);
    this.editForm.controls['areaCode'].setValue(this.user.areaCode);
    this.editForm.controls['cityName'].setValue(this.user.cityName);
    this.editForm.controls['password'].setValue(this.user.password);
    console.log(this.editForm);

    if (this.editForm.valid) {
      const observable = this.userService.editUser(this.user, this.authService.getToken());
      observable.subscribe({
        next: () => {
          this.router.navigate(['/login']);
          this.notification.success(`${this.user.email} has been successfully edited`);
        },
        error: err => {
          console.error(`Error editing user`, err, this.user);
          if (err.status === 409) {
            this.notification.error(`${err.error.detail}`);
          }
        }
      });
    } else {
      this.editForm.markAllAsTouched();
    }
  }
}
