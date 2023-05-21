import {Event} from './event';
import {TicketSeat} from './ticket';

export interface Reservation {
  reservationNr: number;
  event: Event;
  reservationDate: Date;
  reservedSeats: TicketSeat[];
}
