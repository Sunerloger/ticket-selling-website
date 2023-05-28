import { Component, Input } from '@angular/core';
import { PersistedSeat, SeatStatus, SeatType } from 'src/app/dtos/hallplan/hallplan';
import { Section } from 'src/app/dtos/hallplan/section';

interface Size {
  width: number;
  height: number;
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

  /**
   * If this seat is of type "seat" or "standingSeat" then it toggles its selected status
   */
  handleSeatClick(){
    this.isSelected = !this.isSelected;
  }

  isReserved(){
    if(Array.isArray(this.seat.status)){
      return this.seat.status.length > 0;
    }else{
      return this.seat.status !== SeatStatus.free;
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
