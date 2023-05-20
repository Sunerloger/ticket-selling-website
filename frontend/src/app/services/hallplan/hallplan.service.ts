import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Globals } from '../../global/globals';
import { Hallplan, PersistedHallplan, PersistedSeat, PersistedSeatRow, Seat, SeatRow } from 'src/app/dtos/hallplan/hallplan';
import { Section } from 'src/app/dtos/hallplan/section';


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
        )
    }

    createSeatsBulk(hallplanId: number, seatrowId: number, seats: Seat[]){
        console.log("save")
        return this.http.post<PersistedSeatRow>(
            `${this.baseUrl}/${hallplanId}/seatrows/${seatrowId}/seats/bulk`,
            seats
        )
    }

    updateSeatsBulk(hallplanId: number, seatrowId: number, seats: PersistedSeat[]) {
        return this.http.put<PersistedSeatRow>(
            `${this.baseUrl}/${hallplanId}/seatrows/${seatrowId}/seats/bulk`,
            seats
        )
    }

    createSection(hallplanId: number, section: Section){
        return this.http.put<PersistedSeatRow>(
            `${this.baseUrl}/${hallplanId}/sections`,
            section
        )
    }

    /**
     * Delete seatrow by id (including its seats)
     * @param hallplanId id of hallplan
     * @param seatrowId id of seatrow
     */
    deleteSeatrow(hallplanId: number, seatrowId: number){
        return this.http.delete<void>(
            `${this.baseUrl}/${hallplanId}/seatrows/${seatrowId}`
        )
    }
}
