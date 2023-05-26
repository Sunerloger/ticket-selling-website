import {Injectable} from '@angular/core';

import {HttpClient, HttpResponse} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {Reservation} from '../dtos/reservation';
import {SeatDetail} from '../dtos/seatDetail';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private reservationBaseUri: string = this.globals.backendUri + '/reservation';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  getReservations(): Observable<Reservation[]> {
    return this.httpClient.get<Reservation[]>(
      this.reservationBaseUri
    );
  }

  createReservation(seatList: SeatDetail[]): Observable<HttpResponse<any>> {
    return this.httpClient.post<any>(this.reservationBaseUri, seatList);
  }

  deleteReservation(reservationNr: number): Observable<HttpResponse<any>> {
    return this.httpClient.delete<any>(this.reservationBaseUri + '/' + reservationNr);
  }
}
