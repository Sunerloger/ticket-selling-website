import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { PersistedRoomplan, PersistedSeat, PersistedSeatRow, SeatRow, Section } from 'src/app/dtos/hallplan/roomplan';

interface ColorItem {
  backgroundColor: string;
}

const defaultColorOptions: ColorItem[] = [
  {
    backgroundColor: '#DC965A',
  },
  {
    backgroundColor: '#242325',
  },
  {
    backgroundColor: '#BC69AA',
  },
  {
    backgroundColor: '#F05D23',
  },
  {
    backgroundColor: '#6DB1BF',
  },
  {
    backgroundColor: '#3F6C51'
  },
  {
    backgroundColor: '#3D348B'
  }
];

interface CreateSectionPayload extends Section{
  affectedSeatIds: number[];
}

@Component({
  selector: 'app-create-section',
  templateUrl: './create-section.component.html',
  styleUrls: ['./create-section.component.scss']
})
export class CreateSectionComponent implements OnChanges {
  @Input() roomplan: PersistedRoomplan;
  @Output() createSectionEvent = new EventEmitter<CreateSectionPayload>();

  defaultColorOptions = defaultColorOptions;

  name = '';
  fromSeatNr = 0;
  toSeatNr = 0;
  selectedColor = '';
  price = 0;
  selectedRows: SeatRow['rowNr'][] = [];

  nameErrMessage = '';
  fromSeatNrErrMessage = '';
  toSeatNrErrMessage = '';

  createSectionButton = false;

  ngOnChanges(changes: SimpleChanges): void {
    if(this.fromSeatNrErrMessage.length > 0 || this.toSeatNrErrMessage.length > 0){
      this.handleFromSeatChange(String(this.fromSeatNr), changes.roomplan.currentValue as PersistedRoomplan);
      this.handleToSeatChange(String(this.toSeatNr), changes.roomplan.currentValue as PersistedRoomplan);
    };
    this.refreshSelectedRows();
  };

  handleToggleRowSelect(seatrowNr: number){
    const selectedRowsMapCloned = this.selectedRows.slice();
    if(this.selectedRows.includes(seatrowNr)){
      const elemAtIndex = this.selectedRows.indexOf(seatrowNr);
      selectedRowsMapCloned.splice(elemAtIndex, 1); //delete from selectedRow as it was previously already selected
    }else{
      selectedRowsMapCloned.push(seatrowNr); //add to selectedRow as it was previously not selected
    }
    this.selectedRows = selectedRowsMapCloned;


    //as a new selected row may have been added/or removed we must validate fromseatnr and toseatnr again
    this.handleFromSeatChange(this.fromSeatNr.toString());
    this.handleToSeatChange(this.toSeatNr.toString());
  }

  /**
   * Compare selected rows to the seatrows in roomplan. If row is found to be deleted,
   * then it is removed from selectedRow
   */
  refreshSelectedRows(){
    for (let i = 0; i < this.selectedRows.length; i++){
      const found = this.roomplan.seatrows.find((seatrow) => seatrow.rowNr === this.selectedRows[i]);
      if(!found){
        //update state as seatrow did not exist
        const clonedSelectedRows = this.selectedRows.slice();
        clonedSelectedRows.splice(i, 1);
        this.selectedRows = clonedSelectedRows;
      }
    }
  };

  /**
   * Emit an CreateSectionEvent
   */
  handleAddSectionBtnClick(){
    const payload: CreateSectionPayload = {
      name: this.name,
      price: this.price,
      color: this.selectedColor,
      affectedSeatIds: this.affectedSeats()
    };

    this.createSectionEvent.emit(payload);
    console.log(payload);
  }
  /**
   * PRE-COND: smallest possible seatnr is 1
   */
  affectedSeats(): PersistedSeat['id'][]{
    const affectedSeatIds: number[] = [];

    for (let selectedSeatNr = this.fromSeatNr; selectedSeatNr <= this.toSeatNr; selectedSeatNr++){
      for (const seatrow of this.roomplan.seatrows) {
        for(const seat of seatrow.seats){
          if (seat.seatNr === selectedSeatNr){
            affectedSeatIds.push(seat.id);
          }
        }
      }
    }
    return affectedSeatIds;
  }

  isDisabled() {
    const isOk =  this.nameErrMessage.length === 0 && this.fromSeatNrErrMessage.length === 0 &&
      this.toSeatNrErrMessage.length === 0 && this.selectedColor.length > 0 &&
      this.name.length > 0 && this.fromSeatNr > 0 && this.toSeatNr > 0 &&
      this.selectedRows.length > 0;

    return isOk ? null : true;
  }

  handleNameInputChange(updatedName: string){
    if(updatedName.length === 0){
      this.nameErrMessage = 'Name is mandatory';
    }
    this.name = updatedName;
  }

  handleFromSeatChange(updatedFromSeat: string, updatedRoomplan: PersistedRoomplan = this.roomplan){
    this.fromSeatNr = Number(updatedFromSeat); //update state


    if(updatedFromSeat.length > 0 && Number(updatedFromSeat)){
      const newFromSeat = Number(updatedFromSeat);
      for (const seatrow of updatedRoomplan.seatrows) {
        //skip seatrow if not selected...
        if(!this.selectedRows.includes(seatrow.rowNr)){
          continue;
        }

        //...otherwise validate fromseatnr
        if (newFromSeat > 0 && newFromSeat <= seatrow.seats.length) { //seatnr is ensured to be within this interval
          this.fromSeatNrErrMessage = '';
        } else {
          this.fromSeatNrErrMessage = `SeatNr: ${newFromSeat} does not exist in seatrowNr: ${seatrow.rowNr}`;
          return;
        }
      }
    }else{
      this.fromSeatNrErrMessage = 'From SeatNr is mandatory';
    }
  }

  handleToSeatChange(updatedToSeat: string, updatedRoomplan: PersistedRoomplan = this.roomplan) {
    this.toSeatNr = Number(updatedToSeat); //update state

    if (updatedToSeat.length > 0 && Number(updatedToSeat)) {
      const newToSeat = Number(updatedToSeat);
      for (const seatrow of updatedRoomplan.seatrows) {
        //skip seatrow if not selected...
        if (!this.selectedRows.includes(seatrow.rowNr)) {
          continue;
        }

        //...otherwise validate toseatnr
        if(newToSeat < this.fromSeatNr){
          this.toSeatNrErrMessage = `Must be larger or equal to From Seat Nr = ${this.fromSeatNr}`;
        }else if (newToSeat > 0 && newToSeat <= seatrow.seats.length) { //seatnr is ensured to be within this interval
          this.toSeatNrErrMessage = '';
        } else {
          this.toSeatNrErrMessage = `SeatNr: ${newToSeat} does not exist in seatrowNr: ${seatrow.rowNr}`;
          return;
        }
      }
    } else {
      this.toSeatNrErrMessage = 'To SeatNr is mandatory';
    }
  }
}
