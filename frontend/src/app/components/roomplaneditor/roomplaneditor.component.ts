import { Component, OnInit } from '@angular/core';
import { PersistedRoomplan, PersistedSeat, PersistedSeatRow, PersistedSection, Roomplan, Seat, SeatRow, SeatStatus, SeatType } from 'src/app/dtos/roomplan';
import { CreationMenuDirection, SeatCreationEvent } from './seatrow/seatrow.component';
/**
 * Parent Component of all roomplan related components
 */
@Component({
  selector: 'app-roomplaneditor',
  templateUrl: './roomplaneditor.component.html',
  styleUrls: ['./roomplaneditor.component.scss']
})
export class RoomplaneditorComponent implements OnInit {
  roomplan: PersistedRoomplan;
  seatrowMap: Map<SeatRow["rowNr"], SeatRow>;

  ngOnInit(): void {
    this.seatrowMap = new Map();
    this.fetchRoomplan();
  }


  fetchRoomplan() {
    const section: PersistedSection = {
      id: 1,
      color: 'red',
      name: 'Premium Seat',
      price: 30
    }
    const seatRow1: PersistedSeatRow = {
      id: 1,
      rowNr: 1,
      seats: [{
        id: 1,
        type: SeatType.SEAT,
        seatNr: 1,
        status: SeatStatus.FREE,
        section: section
      }]
    }
    const seatRow2: PersistedSeatRow = {
      id: 1,
      rowNr: 2,
      seats: [{
        id: 1,
        type: SeatType.SEAT,
        seatNr: 1,
        status: SeatStatus.FREE,
        section: section
      }]
    }

    const roomplan: PersistedRoomplan = {
      id: 1,
      name: "Room 01",
      description: "A room",
      seatrows: [seatRow1, seatRow2]
    }




    //fetch roomplan
    const fetchedRoomplan = roomplan;

    //set state
    this.roomplan = fetchedRoomplan;


    //add all seatrows to seatrowMap
    for (const seatrow of this.roomplan.seatrows) {
      this.seatrowMap.set(seatrow.rowNr, seatrow);
    }
    console.log(this.seatrowMap)
  }

  /**
   * Add an empty seatrow to roomplan and persists
   * @param rowNr begins with 1
   */
  handleAddRow(rowNr: number) {
    console.log("handleAddRow rowNr=", rowNr);

    //persist new Seat Row
    const persistedEmptySeatRow: PersistedSeatRow = {
      rowNr: rowNr,
      seats: [],
      id: 0
    }

    //update seatRowMap
    this.seatrowMap.set(persistedEmptySeatRow.rowNr, persistedEmptySeatRow);

    //update state
    const clonedRoomplan = structuredClone(this.roomplan);
    clonedRoomplan.seatrows.splice(rowNr - 1, 0, persistedEmptySeatRow);
    this.roomplan = clonedRoomplan;
  }

  handleAddSeat(payload: SeatCreationEvent) {
    console.log("addSeat rowNr=", payload.rowNr, ",type=", payload.type, ",direction=", payload.type);
    const { rowNr, type, direction } = payload;



    //persist new seat
    //TO-DO: add row nr to empty seat or call correct endpoint
    const persistedNewSeat: PersistedSeat = {
      id: 1,
      type: type,
      seatNr: this.getSeatNrFromDirectionAndRowNr(direction, rowNr),
      status: SeatStatus.FREE,
      section: null
    };

    //TO-DO: update seatNr of other seats when direction was left
    console.log(persistedNewSeat)
    //update state
    this.roomplan.seatrows[rowNr - 1].seats.splice(
      persistedNewSeat.seatNr,
      0,
      persistedNewSeat
    );

  }

  getSeatNrFromDirectionAndRowNr(direction: CreationMenuDirection, rowNr: number) {
    switch (direction) {
      case CreationMenuDirection.LEFT:
        return 0;
      case CreationMenuDirection.RIGHT:
        const totalSeats = this.seatrowMap.get(rowNr).seats.length;
        return totalSeats + 1;
    }
  }
}
