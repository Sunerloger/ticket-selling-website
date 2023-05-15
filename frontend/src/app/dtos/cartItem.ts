
export interface CartItem {
  seatNr: number;
  seatId: number;
  rowNr: number;
  section: {
    color: string;
    name: string;
    price: number;
  };
  event: {
    name: string;
    date: string;
  };
  reservation: boolean;
}
