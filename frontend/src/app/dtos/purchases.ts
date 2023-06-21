import {SeatDetail} from './seatDetail';
import {TicketSeat} from './ticket';

export interface CreatePurchase {
  address?: string;
  areaCode?: number;
  city?: string;
  seats: SeatDetail[];
  useUserAddress: boolean;
  creditCardNr: number;
  expiration: string;
  securityCode: number;
}


export interface Purchase {
  purchaseNr: number;
  purchaseDate: Date;
  billAddress: string;
  billCityName: string;
  billAreaCode: number;
  ticketList: TicketSeat[];
  canceled: boolean;
  creditCardNr: number;
  expiration: string;
  securityCode: number;
}
