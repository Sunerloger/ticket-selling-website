import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {AuthService} from '../services/auth.service';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWN1cmUtYmFja2VuZCIsImF1ZCI6InNlY3VyZS1hcHAiLCJzdWIiO' +
    'iJhZG1pbkBlbWFpbC5jb20iLCJleHAiOjE2ODM1MDA1OTcsInJvbCI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl19.4TolSBIgN1QcA2PADS-OOMe' +
    'Vn7gmSDP8hMa221Rul1enb5GI_5C5HuKqQm37-1WJQyKJchBl2zv0qB4fPFApoA';
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
