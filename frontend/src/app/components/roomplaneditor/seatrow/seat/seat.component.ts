import { Component, EventEmitter, Input, Output } from '@angular/core';
import { SeatType } from 'src/app/dtos/hallplan/hallplan';
import { Section } from 'src/app/dtos/hallplan/section';

@Component({
  selector: 'app-seat',
  templateUrl: './seat.component.html',
  styleUrls: ['./seat.component.scss']
})
export class SeatComponent {
  @Input() type: SeatType;
  @Input() id: number;
  @Input() section: Section;

  @Output() seatRemoval = new EventEmitter<number>();

  seatTypeEnum = SeatType;
}
