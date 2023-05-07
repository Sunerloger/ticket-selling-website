import { Component, ElementRef, EventEmitter, HostListener, Output } from '@angular/core';
import { SeatType } from 'src/app/dtos/roomplan';

export interface SeatCreationEventPayload {
  type: SeatType,
  amount: number,
}

@Component({
  selector: 'app-contextmenu',
  templateUrl: './contextmenu.component.html',
  styleUrls: ['./contextmenu.component.scss']
})
export class ContextmenuComponent {
  //state
  seatAmount: number = 1;

  //events
  @Output() seatCreationEvent = new EventEmitter<SeatCreationEventPayload>();
  @Output() outsideCreationClickEvent = new EventEmitter<void>();

  SeatType = SeatType
  private isComponentShown = false;


  constructor(private elementRef: ElementRef) {

  }

  handleSeatAmountChange(amount: number) {
    if (amount >= 1) {
      this.seatAmount = amount;
    }
  }

  /**
 * Emits seatCreationEvent
 * @param direction where to add seat
 * @param type of seat
 */
  onAdd(type: SeatType) {
    this.seatCreationEvent.emit({
      type: type,
      amount: this.seatAmount
    })
  }

  @HostListener('document:click', ['$event'])
  onClick(event: MouseEvent) {
    if (this.isComponentShown && !this.elementRef.nativeElement.contains(event.target)) {
      this.outsideCreationClickEvent.emit();
    } else {
      this.isComponentShown = true;
    }
  }
}
