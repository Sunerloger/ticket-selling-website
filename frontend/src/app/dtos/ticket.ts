
export interface TicketSeat {
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
}
