import { Component, OnInit } from '@angular/core';
import { PersistedHallplan, PersistedSeat, PersistedSeatRow, SeatRow, SeatStatus, SeatType } from 'src/app/dtos/hallplan/hallplan';
import { CreationMenuDirection, SeatCreationEvent, SeatRemovalPayload } from './seatrow/seatrow.component';
import { PersistedSection, Section } from 'src/app/dtos/hallplan/section';
import { HallplanService } from 'src/app/services/hallplan/hallplan.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

/**
 * Parent Component of all roomplan related components
 */
@Component({
  selector: 'app-roomplaneditor',
  templateUrl: './roomplaneditor.component.html',
  styleUrls: ['./roomplaneditor.component.scss']
})
export class RoomplaneditorComponent implements OnInit {
  roomplan: PersistedHallplan = {
    id: 0,
    seatRows: [],
    name: '',
    description: ''
  };

  constructor(
    private service: HallplanService,
    private router: Router,
    private route: ActivatedRoute,
    private notification: ToastrService,
  ) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const hallplanId = params.get('id');
      if(Number(hallplanId)){
        this.fetchHallplanWithId(Number(hallplanId));
      }else{
        this.router.navigate(['/hallplans']);
      }
    });
  }

  fetchHallplanWithId(id: number){
    this.service.getHallplanById(id).subscribe({
      next: data => {
        console.log(data);
        this.roomplan = data;
      },
      error: error => {
        const errorMessage = error.status === 0
          ? 'Server not reachable'
          : error.message.message;
        this.notification.error(errorMessage, 'Requested Hallplan does not exist');
        this.router.navigate(['/hallplans']);
      }
    });
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

    const roomplan: PersistedHallplan = {
      id: 1,
      name: 'Room 01',
      description: 'A room',
      seatRows: [seatRow1, seatRow2]
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
    const emptySeatRow: SeatRow = {
      rowNr,
      seats: [],
    };
    this.service.createSeatrow(this.roomplan.id, emptySeatRow).subscribe({
      next: data => {
        const persistedEmptySeatRow = data;

        //-- update state
        const clonedRoomplan = structuredClone(this.roomplan);

        //update seatrow numbers of other seatrows
        for (const seatrow of clonedRoomplan.seatRows) {
          if (seatrow.rowNr >= rowNr) {
            seatrow.rowNr++; //TO-DO: persist new seatrow
          }
        }

        //add seat
        clonedRoomplan.seatRows.splice(rowNr - 1, 0, persistedEmptySeatRow);
        this.roomplan = clonedRoomplan;
      },
      error: error => {
        const errorMessage = error.status === 0
          ? 'Server not reachable'
          : error.message.message;
        this.notification.error(errorMessage, 'Could not create seatrow. Please try again.');
        this.router.navigate(['/hallplans']);
      }
    });
  }

  handleRemoveRow(deletedRowNr: number) {
    //update state
    const roomplanCloned = structuredClone(this.roomplan);
    roomplanCloned.seatRows.splice(
      deletedRowNr - 1,
      1
    );

    //update other row nr
    for (const seatrow of roomplanCloned.seatRows) {
      if (seatrow.rowNr >= deletedRowNr) {
        seatrow.rowNr--;
      }
    }
    this.roomplan = roomplanCloned;

    //persist roomplan with seatrows
  }

  handleAddSectionToSeats(seatIds: number[], section: Section){
    console.log('handleAddSectionToSeats', seatIds, section);
    //call bulk endpoint

    const updatedSeats: PersistedSeat[] = [];

    //update state
    //just fetch getRoomplan again.. in order to get all the updated seats
  }

  handleAddSeats(payload: SeatCreationEvent) {
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
    const clonedRoomplan = structuredClone(this.roomplan);
    clonedRoomplan.seatRows[rowNr - 1].seats =
    clonedRoomplan.seatRows[rowNr - 1].seats.concat(newSeats);

    this.roomplan = clonedRoomplan;
  }

  handleSeatRemoval(payload: SeatRemovalPayload) {
    console.log(payload);
    const { id, rowNr } = payload;

    const clonedRoomplan = structuredClone(this.roomplan);
    const clonedSeatRow = clonedRoomplan.seatRows[rowNr - 1];
    const clonedSeats = clonedSeatRow.seats;

    for (let i = 0; i < clonedSeats.length; i++) {
      if (clonedSeats[i].id === id) {
        clonedSeats.splice(i, 1);
        break;
      }
    }

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
        const totalSeats = this.roomplan.seatRows[rowNr - 1].seats.length;
        return totalSeats + 1;
    }
  }
}
