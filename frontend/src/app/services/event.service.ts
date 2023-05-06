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
  create(event: Event, token: string): Observable<Event> {
    const headers = new HttpHeaders({
      'Authorization': `${token}`
    });
    console.log(headers);
    return this.http.post<Event>(
      baseUri,
      event,
      {headers}
    );
  }
}
