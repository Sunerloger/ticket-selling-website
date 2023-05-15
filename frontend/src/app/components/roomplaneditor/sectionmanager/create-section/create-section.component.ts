import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { PersistedRoomplan } from 'src/app/dtos/roomplan';

interface ColorItem {
  backgroundColor: string,
}

const defaultColorOptions: ColorItem[] = [
  {
    backgroundColor: "#DC965A",
  },
  {
    backgroundColor: "#242325",
  },
  {
    backgroundColor: "#BC69AA",
  },
  {
    backgroundColor: "#F05D23",
  },
  {
    backgroundColor: "#6DB1BF",
  },
  {
    backgroundColor: "#3F6C51"
  },
  {
    backgroundColor: "#3D348B"
  }
]

@Component({
  selector: 'app-create-section',
  templateUrl: './create-section.component.html',
  styleUrls: ['./create-section.component.scss']
})
export class CreateSectionComponent implements OnChanges {
  @Input() roomplan: PersistedRoomplan;

  defaultColorOptions = defaultColorOptions;
  
  name: string = "";
  fromSeatNr: number = -1;
  toSeatNr: number = -1;
  selectedColor: string = "";
  price: number = 0;

  nameErrMessage = "";
  fromSeatNrErrMessage = "";
  toSeatNrErrMessage = "";

  createSectionButton = false;

  ngOnChanges(changes: SimpleChanges): void {
    console.log(changes)
    if(this.fromSeatNrErrMessage.length > 0 || this.toSeatNrErrMessage.length > 0){
      this.handleFromSeat(String(this.fromSeatNr), changes.roomplan.currentValue as PersistedRoomplan);
      this.handleToSeat(String(this.toSeatNr), changes.roomplan.currentValue as PersistedRoomplan);
    }
  }

  isDisabled() {
    const isOk =  this.nameErrMessage.length === 0 && this.fromSeatNrErrMessage.length === 0 &&
      this.toSeatNrErrMessage.length === 0 && this.selectedColor.length > 0;
  
    return isOk ? null : true;
  }

  handleNameInput(updatedName: string){
    if(updatedName.length === 0){
      this.nameErrMessage = "Name is mandatory"
    }
    this.name = updatedName;
  }

  handleFromSeat(updatedFromSeat: string, updatedRoomplan: PersistedRoomplan = this.roomplan){
    this.fromSeatNr = Number(updatedFromSeat); //update state
 

    if(updatedFromSeat.length > 0 && Number(updatedFromSeat)){
      const newFromSeat = Number(updatedFromSeat);
      for (const seatrow of updatedRoomplan.seatrows) {
        if (newFromSeat > 0 && newFromSeat <= seatrow.seats.length) { //seatnr is ensured to be within this interval
          this.fromSeatNrErrMessage = "";
        } else {
          this.fromSeatNrErrMessage = `SeatNr: ${newFromSeat} does not exist in seatrowNr: ${seatrow.rowNr}`;
          return;
        }
      }
    }else{
      this.fromSeatNrErrMessage = "From SeatNr is mandatory";
    }
  }

  handleToSeat(updatedToSeat: string, updatedRoomplan: PersistedRoomplan = this.roomplan) {
    this.toSeatNr = Number(updatedToSeat); //update state

    if (updatedToSeat.length > 0 && Number(updatedToSeat)) {
      const newToSeat = Number(updatedToSeat);
      for (const seatrow of updatedRoomplan.seatrows) {
        console.log(seatrow, "checkings")
        if(newToSeat < this.fromSeatNr){
          this.toSeatNrErrMessage = `Must be larger or equal to From Seat Nr = ${this.fromSeatNr}`;
        }else if (newToSeat > 0 && newToSeat <= seatrow.seats.length) { //seatnr is ensured to be within this interval
          this.toSeatNrErrMessage = "";
        } else {
          this.toSeatNrErrMessage = `SeatNr: ${newToSeat} does not exist in seatrowNr: ${seatrow.rowNr}`;
          return;
        }
      }
    } else {
      this.toSeatNrErrMessage = "From SeatNr is mandatory";
    }
  }
}
