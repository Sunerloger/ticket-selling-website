import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {User} from '../dtos/user';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userBaseUri: string = this.globals.backendUri + '/register';

  constructor(
    private http: HttpClient,
    private globals: Globals
  ) {
  }

  registerUser(user: User): Observable<User> {
    return this.http.post<User>(
      this.userBaseUri, user
    );

  }
}
