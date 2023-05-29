import {Component, OnInit} from '@angular/core';
import {BlockUser, User} from '../../dtos/user';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {AuthService} from '../../services/auth.service';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {debounceTime, Observable} from 'rxjs';


export enum BlockUnblockMode {
  block,
  unblock
}

@Component({
  selector: 'app-admin-block-unblock',
  templateUrl: './admin-block-unblock.component.html',
  styleUrls: ['./admin-block-unblock.component.scss']
})


export class AdminBlockUnblockComponent implements OnInit {

  user: BlockUser = {
    email: '',
    isLocked: false

  };

  users: BlockUser[] = [];

  blockForm = new FormGroup({
    email: new FormControl(''),
    isLocked: new FormControl(null)
  });
  submitted = false;
  //Error flag
  error = false;
  errorMessage = '';

  mode: BlockUnblockMode = BlockUnblockMode.block;


  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              public authService: AuthService,
              private notification: ToastrService,
              private router: Router) {

  }

  public get heading(): string {
    switch (this.mode) {
      case BlockUnblockMode.block:
        return 'Blocking';
      case BlockUnblockMode.unblock:
        return 'Unblocking';
      default:
        return '?';
    }
  }

  ngOnInit() {
    this.blockForm.get('email').valueChanges.pipe(debounceTime(100)).subscribe(dataValue => {
      this.onChange();
    });
  }

  blockUser(email: string) {
    switch (this.mode) {
      case BlockUnblockMode.block:
        this.blockForm.controls['email'].setValue(email);
        this.blockForm.controls['isLocked'].setValue(true);
        break;
      case BlockUnblockMode.unblock:
        this.blockForm.controls['email'].setValue(email);
        this.blockForm.controls['isLocked'].setValue(false);
        break;
      default:
        console.error('Unknown Mode');
    }
    const user: BlockUser = new BlockUser(this.blockForm.controls['email'].value, this.blockForm.controls['isLocked'].value);
    if (this.blockForm.valid && this.authService.isAdmin()) {
      let observable: Observable<any>;
      switch (this.mode) {
        case BlockUnblockMode.block:
          observable = this.userService.blockUser(user);
          break;
        case BlockUnblockMode.unblock:
          observable = this.userService.unblockUser(user);
          break;
        default:
          console.error('Unknown Mode');
      }
      observable.subscribe({
        next: data => {
          switch (this.mode) {
            case BlockUnblockMode.block:
              this.notification.success(`${this.user.email} has been successfully blocked`);
              break;
            case BlockUnblockMode.unblock:
              this.notification.success(`${this.user.email} has been successfully unblocked`);
              break;
            default:
              console.error('Unknown Mode');
          }
        },
        error: err => {
          console.error(`Error Blocking user`, err, this.user);
          if (err.status === 409) {
            this.notification.error(`${err.error.detail}`);
          }
        }
      });
    }
  }

  onChange() {
    const user = new BlockUser(this.blockForm.controls['email'].value, null);
    console.log(user);
    if (this.mode === 0) {
      user.isLocked = false;
      this.userService.getBlockedUser(user).subscribe((value) => this.users = value);
    } else {
      user.isLocked = true;
      this.userService.getBlockedUser(user).subscribe((value) => this.users = value);
    }
  }

  changeMode() {
    if (this.mode === 0) {
      this.mode = 1;
    } else {
      this.mode = 0;
    }
  }
}
