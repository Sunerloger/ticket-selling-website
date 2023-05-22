import { Component, OnInit } from '@angular/core';
import { PersistedHallplan, PersistedSeat, PersistedSeatRow, Seat, SeatRow, SeatStatus, SeatType } from 'src/app/dtos/hallplan/hallplan';
import { CreationMenuDirection, SeatCreationEvent, SeatRemovalPayload, SeatRowDeletionEventPayload } from './seatrow/seatrow.component';
import { PersistedSection, RESERVED_DEFAULT_SECTION_NAME, Section } from 'src/app/dtos/hallplan/section';
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

  sections: PersistedSection[] = [];

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
        this.fetchAllSections(Number(hallplanId));
      }else{
        this.router.navigate(['/hallplans']);
      }
    });
  }

  fetchAllSections(hallplanId: number){
    this.service.getAllSections(hallplanId).subscribe({
      next: data => {
        console.log(data, ' get all sections');
        this.sections = data;
      },
      error: error => {
        const errorMessage = error.status === 0
          ? 'Server not reachable'
          : error.message.message;
        this.notification.error(errorMessage, 'Failed to retrieve all sections');
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

        //add seatrow
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

  handleRemoveRow(payload: SeatRowDeletionEventPayload) {
    const {rowNr, rowId} = payload;

    //update state
    const roomplanCloned = structuredClone(this.roomplan);
    roomplanCloned.seatRows.splice(
      rowNr - 1,
      1
    );

    //update other row nr
    for (const seatrow of roomplanCloned.seatRows) {
      if (seatrow.rowNr >= rowNr) {
        seatrow.rowNr--;
      }
    }
    this.roomplan = roomplanCloned;

    //persist roomplan with seatrows
    this.service.deleteSeatrow(this.roomplan.id, rowId).subscribe(
      {
        error: error => {
          const errorMessage = error.status === 0
            ? 'Server not reachable'
            : error.message.message;
          this.notification.error(errorMessage, 'Could not create seatrow. Please try again.');
          this.router.navigate(['/hallplans']);
        }
      }
    );
  }

  createSection(section: Section):Promise<PersistedSection>{
    return new Promise((resolve, reject) => {
      this.service.createSection(this.roomplan.id, section).subscribe({
        next: data => {
          resolve(data);
        },
        error: error => {
          const errorMessage = error.status === 0
            ? 'Server not reachable'
            : error.message.message;
          this.notification.error(errorMessage, 'Could not create section. Please try again.');
          reject(error);
        }
      });
    });
  }

  async handleAddSectionToSeats(affectedSeats: PersistedSeat[], section: Section){
    console.log('handleAddSectionToSeats', affectedSeats, section);
    //create section
    const createdSection = await this.createSection(section);

    //assign newly created section to all affected seats
    for(const seat of affectedSeats){
      seat.section = createdSection;
    }

    //update affectedseats with section
    this.service.updateSeatsBulk(this.roomplan.id, affectedSeats).subscribe({
      next: () => {
        this.notification.success(`Successfully created Section '${section.name}'`);

        //fetch hallplan in order to get all the updated seats
        this.fetchHallplanWithId(this.roomplan.id);
      },
      error: error => {
        const errorMessage = error.status === 0
          ? 'Server not reachable'
          : error.message.message;
        this.notification.error(errorMessage, 'Could not create section. Please try again.');
      }
    });
  }

  async updateSeatsBulk(updatedSeats: PersistedSeat[], errorMessage: string){
    return new Promise((resolve, reject) => {
      this.service.updateSeatsBulk(
        this.roomplan.id,
        updatedSeats
      ).subscribe({
        next: data => {
          resolve(data);
        },
        error: error => {
          const errorMessageDefault = error.status === 0
            ? 'Server not reachable'
            : error.message.message;
          this.notification.error(errorMessageDefault, errorMessage);
          reject(error);
        }
      });
    });
  }

  async createDefaultSection(): Promise<PersistedSection>{
    const defaultSection: Section = {
      name: RESERVED_DEFAULT_SECTION_NAME,
      color: 'white',
      price: 0
    };

    return new Promise((resolve, reject) => {
      this.service.createSection(this.roomplan.id, defaultSection).subscribe(
        {
          next: data => {
            resolve(data);
          },
          error: error => {
            const errorMessage = error.status === 0
              ? 'Server not reachable'
              : error.message.message;
            this.notification.error(errorMessage, errorMessage);
            reject(error);
          }
        }
      );
    });
  }

  /**
   * Return default section "Unassigned" when it does not exist, the section will be
   * created
   * There is one section called "Unassigned" which function as the default
   * section for every seat of a hallplan
   *
   */
 async retrieveDefaultSection(): Promise<PersistedSection|null>{
  const defaultSectionName = 'Unassigned';
   const defaultPersistedSection = this.sections.find(section => section.name === defaultSectionName);

   return new Promise(async (resolve, reject) => {
     if (defaultPersistedSection) {
       return resolve(defaultPersistedSection);
     } else {
      const defaultSection = await this.createDefaultSection();
      if(defaultSection){
        resolve(defaultSection);
      }else{
        reject('Error creating default section');
      }
     }
   });
  }

  async handleAddSeats(payload: SeatCreationEvent) {
    console.log('addSeat rowNr=', payload.rowNr, ',type=', payload.type, ',direction=', payload.type);
    const { rowNr, type, direction, amountSeat } = payload;

    const initialSeatNr = this.getLatestSeatNrFromDirectionAndRowNr(direction, rowNr);
    const newSeats: Seat[] = [];

    // --- create seats that needs to be created
    switch(direction){
      case CreationMenuDirection.left:
        // --- update seatNr of other seats when direction was left
        const updateSeats: PersistedSeat[] = [];

        let seatNrOfOldSeat = amountSeat + 1;
        for(const seat of this.roomplan.seatRows[rowNr - 1].seats){
          updateSeats.push({ ...seat, seatNr: seatNrOfOldSeat });
          seatNrOfOldSeat++;
        }
        // persist
        if(updateSeats.length > 0){
          await this.updateSeatsBulk(updateSeats, 'Failed to add seats. Please try again.');
        }


        // --- generate seats that needs to be persisted
        let newSeatNr = 1;
        for(let i = 0; i < amountSeat; i++){
          newSeats.push(
            await this.createEmptySeat(type, newSeatNr)
          );
          newSeatNr++;
        }
        break;
      case CreationMenuDirection.right:
        // --- generate seats that needs to be persisted
        let highestSeatNr = initialSeatNr;
        for (let i = 0; i < amountSeat; i++) {
          newSeats.push(
            await this.createEmptySeat(type, highestSeatNr)
          );
          highestSeatNr++;
        }
        break;
    }

    // --- persist newly created seats
    this.service.createSeatsBulk(
      this.roomplan.id,
      this.roomplan.seatRows[rowNr - 1].id,
      newSeats
    ).subscribe({
      next: () => {
        // --- update state
        this.fetchHallplanWithId(this.roomplan.id);
      },
      error: error => {
        const errorMessage = error.status === 0
          ? 'Server not reachable'
          : error.message.message;
        this.notification.error(errorMessage, 'Failed adding new seats. Please try again.');
      }
    });
  }

  async handleSeatRemoval(payload: SeatRemovalPayload) {
    const { id, rowNr } = payload;

    const clonedRoomplan = structuredClone(this.roomplan);
    const clonedSeatRow = clonedRoomplan.seatRows[rowNr - 1];
    const clonedSeats = clonedSeatRow.seats;

    let deletedSeatIndex = -1; 
    const updatedSeatsWithNewSeatNr: PersistedSeat[] = []
    for (let i = 0; i < clonedSeats.length; i++) {
      if (clonedSeats[i].id === id) {
        deletedSeatIndex = i;
        clonedSeats.splice(i, 1);
        continue;
      }
      //update the seatnumbers after deleted seats
      if(deletedSeatIndex !== -1){ 
        //we already found the deletedseat after that all seats should have their seat nr adjusted
        clonedSeats[i].seatNr--;
        updatedSeatsWithNewSeatNr.push(clonedSeats[i]);
      }
    };

    //persist new seats
    await this.updateSeatsBulk(updatedSeatsWithNewSeatNr, "Failed to remove seat. Please try again");

    //persist new seat removal
    this.service.deleteSeat(this.roomplan.id, this.roomplan.seatRows[rowNr - 1].id, id).subscribe({
      next: () => {
        this.roomplan = clonedRoomplan;
      },
      error: error => {
        const errorMessage = error.status === 0
          ? 'Server not reachable'
          : error.message.message;
        this.notification.error(errorMessage, 'Failed removing seat. Please try again.');
      }
    })
  }

  /**
   * Return empty seat with default section
   *
   * @param type
   * @param seatNr
   * @param capacity
   * @returns
   */
  async createEmptySeat(type: SeatType, seatNr: number, capacity?: number): Promise<Seat> {
    const defaultSection = await this.retrieveDefaultSection();

    switch(type){
      case SeatType.seat:
        capacity = 1;
        break;
      case SeatType.vacantSeat:
        capacity = 0;
        break;
      case SeatType.standingSeat:
        capacity = capacity ?? 100;
        break;
      default:
        capacity = 1;
    }
    const emptySeat: Seat = {
      type,
      seatNr,
      status: SeatStatus.free,
      section: defaultSection,
      capacity
    };
    return emptySeat;
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
