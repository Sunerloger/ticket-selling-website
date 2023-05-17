import {Event} from './event';

export interface CartItem {
  seat: {
    id: number;
    status: string;
    type?: string;
    capacity?: number;
    seatNr: number;
    section: {
      id: number;
      color: string;
      name: string;
      price: number;
    };
    seatrowId: number;
  };
  event: Event;
}
