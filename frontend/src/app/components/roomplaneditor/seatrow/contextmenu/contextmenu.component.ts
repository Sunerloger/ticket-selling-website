import { Component, ElementRef, EventEmitter, HostListener, Output } from '@angular/core';

export enum SeatType {
  VACANT,
  FILLED
}

@Component({
  selector: 'app-contextmenu',
  templateUrl: './contextmenu.component.html',
  styleUrls: ['./contextmenu.component.scss']
})
export class ContextmenuComponent {
  @Output() seatCreationEvent = new EventEmitter<SeatType>();
  @Output() outsideCreationClickEvent = new EventEmitter<void>();
  SeatType = SeatType

  private isComponentShown = false;


  constructor(private elementRef: ElementRef) {

  }

  /**
 * Emits seatCreationEvent
 * @param direction where to add seat
 * @param type of seat
 */
  onAdd(type: SeatType) {
    this.seatCreationEvent.emit(type)
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
