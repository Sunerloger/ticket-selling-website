import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PersistedSeatRow, SeatType } from 'src/app/dtos/roomplan';
import { SeatCreationEventPayload } from './contextmenu/contextmenu.component';


export enum CreationMenuDirection {
  LEFT,
  RIGHT
}

export interface SeatCreationEvent {
  direction: CreationMenuDirection,
  type: SeatType,
  amountSeat: number,
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
  @Input() showDeleteRowBtn: boolean = false;
  @Input() showAddRowBtn: boolean = false;

  //events
  @Output() onSeatCreationEvent = new EventEmitter<SeatCreationEvent>()
  @Output() onSeatRowDeletion = new EventEmitter<number>()

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
  handleAddSeat(rowNr: number, direction: CreationMenuDirection, payload: SeatCreationEventPayload) {
    const { amount, type } = payload;

    this.onSeatCreationEvent.emit({ rowNr, direction, type, amountSeat: amount });

    this.closeActiveContextMenu();
  }
}
