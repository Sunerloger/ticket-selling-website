import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {AuthService} from '../services/auth.service';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWN1cmUtYmFja2VuZCIsImF1ZCI6InNlY3VyZS1hcHAiLCJzdWIiOiJhZG1' +
    'pbkBlbWFpbC5jb20iLCJleHAiOjE2ODM5MzkzODAsInJvbCI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl19.fFpyGPZn1vYPV8LC9dbnBaT' +
    '9HSnN-bZDUBsbQcFkmA786PIr41pc0hTEA0euHodmZBpVwDQ0uqSrcx9FlDS5nw';
  constructor(private authService: AuthService, private globals: Globals) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authUri = this.globals.backendUri + '/authentication';

    // Do not intercept authentication requests
    if (req.url === authUri) {
      return next.handle(req);
    }

    const authReq = req.clone({
      //headers: req.headers.set('Authorization', 'Bearer ' + this.authService.getToken())
      //temporary fix while login/register not implemented
      headers: req.headers.set('Authorization', 'Bearer ' + this.token)
    });

    return next.handle(authReq);
  }
}
