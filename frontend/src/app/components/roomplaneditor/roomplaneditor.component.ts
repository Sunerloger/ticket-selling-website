import { Component, OnInit } from '@angular/core';
import { PersistedRoomplan, PersistedSeat, PersistedSeatRow, PersistedSection, SeatStatus, SeatType } from 'src/app/dtos/roomplan';
import { CreationMenuDirection, SeatCreationEvent, SeatRemovalPayload } from './seatrow/seatrow.component';
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

  ngOnInit(): void {
    this.fetchRoomplan();
  }


  fetchRoomplan() {
    const section: PersistedSection = {
      id: 1,
      color: 'red',
      name: 'Premium Seat',
      price: 30
    };
    const seatRow1: PersistedSeatRow = {
      id: 1,
      rowNr: 1,
      seats: [{
        id: 1,
        type: SeatType.seat,
        seatNr: 1,
        status: SeatStatus.free,
        section
      }]
    };
    const seatRow2: PersistedSeatRow = {
      id: 1,
      rowNr: 2,
      seats: [{
        id: 1,
        type: SeatType.seat,
        seatNr: 1,
        status: SeatStatus.free,
        section
      },
      {
        id: 2,
        type: SeatType.seat,
        seatNr: 2,
        status: SeatStatus.free,
        section
      }]
    };

    const roomplan: PersistedRoomplan = {
      id: 1,
      name: 'Room 01',
      description: 'A room',
      seatrows: [seatRow1, seatRow2]
    };

    //fetch roomplan
    const fetchedRoomplan = roomplan;

    //set state
    this.roomplan = fetchedRoomplan;
  }

  /**
   * Add an empty seatrow to roomplan and persists
   *
   * @param rowNr begins with 1
   */
  handleAddRow(rowNr: number) {
    console.log('handleAddRow rowNr=', rowNr);

    //-- persist new Seat Row
    const persistedEmptySeatRow: PersistedSeatRow = {
      rowNr,
      seats: [],
      id: 0
    };


    //-- update state
    const clonedRoomplan = structuredClone(this.roomplan);

    //update seatrow numbers of other seatrows
    for (const seatrow of clonedRoomplan.seatrows) {
      if (seatrow.rowNr >= rowNr) {
        seatrow.rowNr++; //TO-DO: persist new seatrow
      }
    }

    //add seat
    clonedRoomplan.seatrows.splice(rowNr - 1, 0, persistedEmptySeatRow);
    this.roomplan = clonedRoomplan;
  }

  handleRemoveRow(deletedRowNr: number) {
    //update state
    const roomplanCloned = structuredClone(this.roomplan);
    roomplanCloned.seatrows.splice(
      deletedRowNr - 1,
      1
    );

    //update other row nr
    for (const seatrow of roomplanCloned.seatrows) {
      if (seatrow.rowNr >= deletedRowNr) {
        seatrow.rowNr--;
      }
    }
    this.roomplan = roomplanCloned;

    //persist roomplan with seatrows
  }

  handleAddSeat(payload: SeatCreationEvent) {
    console.log('addSeat rowNr=', payload.rowNr, ',type=', payload.type, ',direction=', payload.type);
    const { rowNr, type, direction, amountSeat } = payload;

    const newSeats = [];
    const initialSeatNr = this.getLatestSeatNrFromDirectionAndRowNr(direction, rowNr);
    let searNr = initialSeatNr;

    //TO-DO: add row nr to empty seat or call correct endpoint
    for (let i = 0; i < amountSeat; i++) {
      newSeats.push(
        this.createEmptySeat(type, searNr) //persist new seat
      );
      searNr++;
    }

    //TO-DO: update seatNr of other seats when direction was left

    //update state
    this.roomplan.seatrows[rowNr - 1].seats =
      this.roomplan.seatrows[rowNr - 1].seats.concat(newSeats);
  }

  handleSeatRemoval(payload: SeatRemovalPayload) {
    console.log(payload);
    const { id, rowNr } = payload;

    const clonedRoomplan = structuredClone(this.roomplan);
    const clonedSeatRow = clonedRoomplan.seatrows[rowNr - 1];
    const clonedSeats = clonedSeatRow.seats;

    for (let i = 0; i < clonedSeats.length; i++) {
      if (clonedSeats[i].id === id) {
        clonedSeats.splice(i, 1);
        break;
      }
    };

    this.roomplan = clonedRoomplan;

    //TO-DO: persist new seatrow

  }

  createEmptySeat(type: SeatType, seatNr: number): PersistedSeat {
    const persistedNewSeat: PersistedSeat = {
      id: 1,
      type,
      seatNr,
      status: SeatStatus.free,
      section: null
    };
    return persistedNewSeat;
  }

  getLatestSeatNrFromDirectionAndRowNr(direction: CreationMenuDirection, rowNr: number) {
    switch (direction) {
      case CreationMenuDirection.left:
        return 0;
      case CreationMenuDirection.right:
        const totalSeats = this.roomplan.seatrows[rowNr - 1].seats.length;
        return totalSeats + 1;
    }
  }
}
