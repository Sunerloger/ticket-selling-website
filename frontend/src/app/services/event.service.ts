import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
const baseUri = environment.backendUrl + '/api/v1/events';
import {Event} from 'src/app/dtos/event';
@Injectable({
  providedIn: 'root'
})
export class EventService {
  constructor(
    private http: HttpClient,
  ) { }
  /**
   * Create a new event in the system.
   *
   * @param event the data for the horse that should be created
   * @param token the token that is needed for authentication
   * @return an Observable for the created horse
   */
  create(event: Event): Observable<Event> {
    const token = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWN1cmUtYmFja2VuZCIsImF1ZCI6InNlY3VyZS1hcHAiLCJzdWIiO' +
      'iJhZG1pbkBlbWFpbC5jb20iLCJleHAiOjE2ODM1MDA1OTcsInJvbCI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl19.4TolSBIgN1QcA2PADS-OOMe' +
      'Vn7gmSDP8hMa221Rul1enb5GI_5C5HuKqQm37-1WJQyKJchBl2zv0qB4fPFApoA';
    console.log(token);
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': ` ${token}`,
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'POST',
        'Access-Control-Allow-Headers': 'Content-Type, Authorization'
      })
    };
    console.log(httpOptions);
    console.log(event);
    return this.http.post<Event>(
      baseUri,
      event,
      httpOptions
    );
  }
}
