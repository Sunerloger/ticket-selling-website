import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Globals } from '../../global/globals';
import { Hallplan, PersistedHallplan, PersistedSeat, PersistedSeatRow, SeatRow } from 'src/app/dtos/hallplan/hallplan';


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

    createSeat(hallplanId: number, seatrowId: number, seat: PersistedSeat){
        return this.http.post<PersistedSeatRow>(
            `${this.baseUrl}/${hallplanId}/seatrows/${seatrowId}/seat/bulk`,
            seat
        )
    }
}
