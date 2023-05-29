import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PersistedSeat, SeatStatus, SeatType } from 'src/app/dtos/hallplan/hallplan';
import { Section } from 'src/app/dtos/hallplan/section';

interface Size {
  width: number;
  height: number;
}

export interface SeatSelectionPayload{
  seat: PersistedSeat,
  isSelected: boolean
}

@Component({
  selector: 'app-immutableseat',
  templateUrl: './immutableseat.component.html',
  styleUrls: ['./immutableseat.component.scss']
})
export class ImmutableseatComponent {
  @Input() seat: PersistedSeat;
  isSelected = false;

  seatTypeEnum = SeatType;

  @Output() seatSelectionChangeEvent = new EventEmitter<SeatSelectionPayload>();

  /**
   * If this seat is of type "seat" or "standingSeat" then it toggles its selected status
   */
  handleSeatClick(){
    const newSelectStatus = !this.isSelected;
    
    //update state
    this.isSelected = newSelectStatus;
    
    //emit event
    this.seatSelectionChangeEvent.emit({
      seat: this.seat,
      isSelected: newSelectStatus
    });
  }

  /**
   * Returns true if this seat is available for buy/reservation process
   * @returns false if this seat has status free otherwise true
   */
  isAvailable(){
    if(Array.isArray(this.seat.status)){
      return this.seat.status.length > 0;
    }else{
      return this.seat.status === SeatStatus.free;
    }
  }

  calcWidthAndHeightAssCSSProperties(): Size {
    const width = this.seat.capacity * 0.05;
    const height = this.seat.capacity * 0.03;
    return {
      width,
      height
    };
  }
}
