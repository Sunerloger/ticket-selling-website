
/*
    Section
*/

import { PersistedSection, Section } from './section';

export interface Hallplan {
    name: string;
    description: string;
    seatRows: SeatRow[];
}

export interface PersistedHallplan extends Hallplan {
    id: number;
    seatRows: PersistedSeatRow[];
}

/*
    Seat Row
*/

export interface SeatRow {
    rowNr: number;
    seats: Seat[];
}

export interface PersistedSeatRow extends SeatRow {
    id: number;
    seats: PersistedSeat[];
}

/*
    Seat
*/

export interface Seat {
    type: SeatType;
    seatNr: number;
    status: SeatStatus;
    section: Section | null;
}

export interface PersistedSeat extends Seat {
    id: number;
    section: PersistedSection | null;
}

/*
    Enum
*/

export enum SeatStatus {
    reserved = 'RESERVED',
    occupied = 'OCCUPIED',
    free = 'FREE'
}

export enum SeatType {
    seat,
    vacantSeat,
}
