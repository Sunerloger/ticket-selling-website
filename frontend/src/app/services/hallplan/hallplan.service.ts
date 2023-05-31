import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Globals } from '../../global/globals';
import { Hallplan, PersistedHallplan, PersistedSeat, PersistedSeatRow, Seat, SeatRow } from 'src/app/dtos/hallplan/hallplan';
import { PersistedSection, Section } from 'src/app/dtos/hallplan/section';
import {Observable} from 'rxjs';
import {AbbreviatedHallplan} from '../../dtos/hallplan/abbreviatedHallplan';


@Injectable({
    providedIn: 'root'
})
export class HallplanService {

    private baseUrl: string = this.globals.backendUri + '/hallplans';

    constructor(private http: HttpClient, private globals: Globals) {
    }

    getAllHallplans() {
        return this.http.get<Hallplan[]>(
          `${this.baseUrl}`
        );
    }

    getHallplanById(id: number){
        return this.http.get<PersistedHallplan>(
            `${this.baseUrl}/${id}`
        );
    }

    createSeatrow(hallplanId: number, seatrow: SeatRow){
        return this.http.post<PersistedSeatRow>(
            `${this.baseUrl}/${hallplanId}/seatrows`,
            seatrow
        );
    }

    /*

        --------------------- Seats Endpoints ------------------------------

    */

    createSeatsBulk(hallplanId: number, seatrowId: number, seats: Seat[]){
        return this.http.post<PersistedSeatRow>(
            `${this.baseUrl}/${hallplanId}/seatrows/${seatrowId}/seats/bulk`,
            {
                seats
            }
        );
    }

    updateSeatsBulk(hallplanId: number, seats: PersistedSeat[]) {
        return this.http.put<PersistedSeatRow>(
            `${this.baseUrl}/${hallplanId}/seats/bulk`,
            {
                seats
            }
        );
    }

    deleteSeat(hallplanId: number, seatrowId: number, seatId: number){
        return this.http.delete<void>(
            `${this.baseUrl}/${hallplanId}/seatrows/${seatrowId}/seats/${seatId}`
        );
    }

    /*

        --------------------- Section Endpoints ------------------------------

    */

    getAllSections(hallplanId: number) {
        return this.http.get<PersistedSection[]>(
            `${this.baseUrl}/${hallplanId}/sections`,
        );
    }

    createSection(hallplanId: number, section: Section){
        return this.http.post<PersistedSection>(
            `${this.baseUrl}/${hallplanId}/sections`,
            section
        );
    }

    deleteSection( sectionId: number){
        return this.http.delete<PersistedSection>(
            `${this.baseUrl}/sections/${sectionId}`
        );
    }

    /**
     * Delete seatrow by id (including its seats)
     *
     * @param hallplanId id of hallplan
     * @param seatrowId id of seatrow
     */
    deleteSeatrow(hallplanId: number, seatrowId: number){
        return this.http.delete<void>(
            `${this.baseUrl}/${hallplanId}/seatrows/${seatrowId}`
        );
    }

  /**
   * Get Roomplans by pages.
   *
   * @param pageIndex index of the searched for page
   */
  getRoomplans(pageIndex: number): Observable<AbbreviatedHallplan[]> {
    let params: HttpParams = new HttpParams();
    params = params.set('pageIndex', pageIndex);
    return this.http.get<AbbreviatedHallplan[]>(this.baseUrl+'/search', {params});
  }

  /**
   * Get a AbbreviatedHallplan by its id.
   *
   * @param id the id of the needed hallplan
   */
  getByIdAbbreviated(id: number): Observable<AbbreviatedHallplan> {
    let params: HttpParams = new HttpParams();
    params = params.set('id', id);
    return this.http.get<AbbreviatedHallplan>(this.baseUrl+'/byId', {params});
  }
}
