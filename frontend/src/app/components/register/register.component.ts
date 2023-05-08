import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../../services/user.service';
import {User} from '../../dtos/user';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  user: User = {
    email: '',
    firstName: '',
    lastName: '',
    birthdate: new Date(),
    address: '',
    areaCode: 0,
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
              private router: Router) {

  }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      email: [''],
      firstName: [''],
      lastName: [''],
      birthdate: [''],
      address: [''],
      areaCode: [''],
      cityName: [''],
      password: ['']
    });
  }

  registerUser() {
    console.log(this.user);
    const observable = this.userService.registerUser(this.user);
    observable.subscribe({
      next: data => {
        console.log('User successfully created');
        this.router.navigate(['/login']);
      }
    });

  }


}
