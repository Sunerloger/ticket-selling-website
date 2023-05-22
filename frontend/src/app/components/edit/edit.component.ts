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
      console.log('User ',user);
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

}
