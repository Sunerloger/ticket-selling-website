import { Component, OnInit } from '@angular/core';
import { Roomplan, SeatStatus, SeatType } from 'src/app/dtos/roomplan';

@Component({
  selector: 'app-roomplaneditor',
  templateUrl: './roomplaneditor.component.html',
  styleUrls: ['./roomplaneditor.component.scss']
})
export class RoomplaneditorComponent implements OnInit {
  roomplan: Roomplan

  ngOnInit(): void {
    this.roomplan = {
      id: 1,
      name: "Room 01",
      description: "A room",
      sections: [{
        id: 1,
        price: 100,
        color: "red",
        name: "Primary Seat",
        seats: [{
          id: 1,
          type: SeatType.SEAT,
          rowNr: 1,
          seatNr: 1,
          status: SeatStatus.FREE
        }]
      }]
    }
  }
}
