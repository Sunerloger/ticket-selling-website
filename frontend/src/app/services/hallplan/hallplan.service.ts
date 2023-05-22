import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Globals } from '../../global/globals';
import { Hallplan, PersistedHallplan, PersistedSeat, PersistedSeatRow, Seat, SeatRow } from 'src/app/dtos/hallplan/hallplan';
import { PersistedSection, Section } from 'src/app/dtos/hallplan/section';


@Injectable({
    providedIn: 'root'
})
export class HallplanService {

    private baseUrl: string = this.globals.backendUri + '/hallplans';

    constructor(private http: HttpClient, private globals: Globals) {
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
}
