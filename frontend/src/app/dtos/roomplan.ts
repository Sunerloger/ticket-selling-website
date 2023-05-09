
export interface RoomplanItem {
  seatNr: number;
  seatId: number;
  rowNr: number;
  section: {
    color: string;
    name: string;
    price: number;
  };
}
