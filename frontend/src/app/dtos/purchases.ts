import {SeatDetail} from './seatDetail';
import {TicketSeat} from './ticket';

export interface CreatePurchase {
  address?: string;
  areaCode?: number;
  city?: string;
  seats: SeatDetail[];
  useUserAddress: boolean;

}


export interface Purchase {
  purchaseNr: number;
  purchaseDate: Date;
  billAddress: string;
  billCityName: string;
  billAreaCode: number;
  ticketList: TicketSeat[];

}
