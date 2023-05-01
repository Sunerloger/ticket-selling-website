import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PersistedSeatRow, SeatType } from 'src/app/dtos/roomplan';


export enum CreationMenuDirection {
  LEFT,
  RIGHT
}

export interface SeatCreationEvent {
  direction: CreationMenuDirection,
  type: SeatType,
  rowNr: number,
}

@Component({
  selector: 'app-seatrow',
  templateUrl: './seatrow.component.html',
  styleUrls: ['./seatrow.component.scss']
})
export class SeatrowComponent {
  //references to typescript enums
  CreationMenuDirection = CreationMenuDirection;
  SeatType = SeatType;

  //state variables
  showCreationMenuRight: boolean = false;
  showCreationMenuLeft: boolean = false;

  //props
  @Input() seatRow: PersistedSeatRow;

  //events
  @Output() onSeatCreationEvent = new EventEmitter<SeatCreationEvent>()

  /**
   * Handles click outside of context menu
   * @param from 
   */
  handleContextMenuOutsideClick(from: CreationMenuDirection) {
    switch (from) {
      case CreationMenuDirection.LEFT:
        this.showCreationMenuLeft = false;
        break;
      case CreationMenuDirection.RIGHT:
        this.showCreationMenuRight = false;
        break;
    }
  }

  /**
   * Close Context Menu
   */
  closeActiveContextMenu() {
    this.showCreationMenuLeft = false;
    this.showCreationMenuRight = false;
  }

  /**
   * Outputs seatCreationEvent when type is not vacant
   * @param direction 
   * @param type 
   */
  handleAddSeat(rowNr: number, direction: CreationMenuDirection, type: SeatType) {
    console.log(direction, type);

    this.onSeatCreationEvent.emit({ rowNr, direction, type });

    this.closeActiveContextMenu();
  }
}
