export interface Roomplan {
    id: number,
    name: string,
    description: string,
    sections: Section[]
}

export interface Section {
    id: number,
    color: string,
    name: string,
    price: number,
    seats: Seat[]
}

export interface Seat {
    id: number
    type: SeatType
    rowNr: number,
    seatNr: number,
    status: SeatStatus,
}

export enum SeatStatus {
    RESERVED = "RESERVED",
    OCCUPIED = "OCCUPIED",
    FREE = "FREE"
}

export enum SeatType {
    SEAT,
    VACANT_SEAT,
}