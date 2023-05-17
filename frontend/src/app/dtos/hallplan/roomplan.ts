
/*
    Section
*/

export interface Roomplan {
    name: string;
    description: string;
    seatrows: SeatRow[];
}

export interface PersistedRoomplan extends Roomplan {
    id: number;
    seatrows: PersistedSeatRow[];
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
    section: Section;
}

export interface PersistedSeat extends Seat {
    id: number;
    section: PersistedSection;
}

/*
    Section
*/

export interface Section {
    color: string;
    name: string;
    price: number;
}

export interface PersistedSection extends Section {
    id: number;
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
