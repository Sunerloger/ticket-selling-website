import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {from, Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
const baseUri = environment.backendUrl + '/api/v1/events';
import {Event} from 'src/app/dtos/event';
import {AbbreviatedEvent} from '../dtos/abbreviatedEvents';
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
    console.log(event);
    return this.http.post<Event>(
      baseUri,
      event
    );
  }
  /**
   * Get all events stored in the system.
   *
   * @return observable list of found events.
   */
  getAll(): Observable<any> {
    return this.http.get<any>(baseUri);
  }

  /**
   * Loads an event-page from the backend
   *
   * @param pageIndex index of the page that should be fetched
   * @return an Observable for the fetched page of event entries
   */
  getPage(pageIndex: number, fromDate: string, toDate: string, artist: string, location: string ): Observable<AbbreviatedEvent[]> {
    let params: HttpParams = new HttpParams();
    params = params.set('pageIndex', pageIndex);
    if(fromDate != null){
      params = params.set('fromDate', fromDate);
    }
    if(toDate != null){
      params = params.set('untilDate', toDate);
    }
    if(artist !== ''){
      params = params.set('artist', artist);
    }
    if(location !== ''){
      params = params.set('location', location);
    }
    return this.http.get<AbbreviatedEvent[]>(baseUri, {params});
  }

}
