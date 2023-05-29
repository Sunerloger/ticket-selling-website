import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {BlockUser, User} from '../dtos/user';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Globals} from '../global/globals';
import {AuthService} from './auth.service';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userBaseUri: string = this.globals.backendUri + '/register';
  private adminBaseUri: string = this.globals.backendUri + '/admin';
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

  editUser(user: User, token: string): Observable<User> {
    if (this.authService.isLoggedIn()) {
      return this.http.put<User>(
        this.userGetUri + '?token=' + token, user
      );
    }
  }

  delete(id: number, email: string, password: string) {
    console.log(id + email + password);
    let params = new HttpParams();
    params = params.append('id', id);
    params = params.append('email', email);
    params = params.append('password', password);
    return this.http.delete(this.userGetUri, {params});
  }

  blockUser(blockUser: BlockUser) {
    console.log(blockUser.email);
    console.log(blockUser.isLocked);
    return this.http.put(this.adminBaseUri, blockUser);
  }

  getBlockedUser(user: BlockUser): Observable<BlockUser[]> {
    let params = new HttpParams();
    if (user.email) {
      params = params.append('email', user.email);
    }
    params = params.append('isLocked', user.isLocked);
    return this.http.get<BlockUser[]>(this.adminBaseUri, {params});
  }

  unblockUser(unblockUser: BlockUser) {
    console.log(unblockUser.email);
    console.log(unblockUser.isLocked);
    return this.http.put(this.adminBaseUri, unblockUser);
  }

}

