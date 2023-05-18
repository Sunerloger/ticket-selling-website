import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SeatType } from 'src/app/dtos/hallplan/hallplan';

@Component({
  selector: 'app-seat',
  templateUrl: './seat.component.html',
  styleUrls: ['./seat.component.scss']
})
export class SeatComponent {
  @Input() type: SeatType;
  @Input() id: number;

  @Output() seatRemoval = new EventEmitter<number>();

  seatTypeEnum = SeatType;
}
