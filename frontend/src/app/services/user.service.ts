import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {User} from '../dtos/user';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {AuthService} from './auth.service';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userBaseUri: string = this.globals.backendUri + '/register';
  private adminBaseUri: string = this.globals.backendUri + '/user';
  private userGetUri: string = this.globals.backendUri + '/edit';

  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private globals: Globals
  ) {
  }

  registerUser(user: User): Observable<User> {
    if (this.authService.isLoggedIn() && this.authService.getUserRole() === 'ADMIN') {
      return this.http.post<User>(
        this.adminBaseUri, user
      );
    } else {
      return this.http.post<User>(
        this.userBaseUri, user
      );
    }
  }

  getUser(token: string): Observable<User> {
    if (this.authService.isLoggedIn()) {
      return this.http.get<User>(
        this.userGetUri + '?token=' + token
      );
    }
  }

  editUser(user: User): Observable<User> {
    if (this.authService.isLoggedIn()) {
      return this.http.post<User>(
        this.userGetUri, user
      );
    }
  }
}
