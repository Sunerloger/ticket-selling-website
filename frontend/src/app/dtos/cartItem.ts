import {Event} from './event';
import {TicketSeat} from './ticket';

export interface CartItem {
  seat: TicketSeat;
  event: Event;
}
