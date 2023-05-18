import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PersistedSeatRow, SeatType } from 'src/app/dtos/hallplan/hallplan';
import { SeatCreationEventPayload } from './contextmenu/contextmenu.component';
import { EventManager } from '@angular/platform-browser';

export interface SeatRemovalPayload {
  id: number;
  rowNr: number;
}

export enum CreationMenuDirection {
  left,
  right
}

export interface SeatCreationEvent {
  direction: CreationMenuDirection;
  type: SeatType;
  amountSeat: number;
  rowNr: number;
}

@Component({
  selector: 'app-seatrow',
  templateUrl: './seatrow.component.html',
  styleUrls: ['./seatrow.component.scss']
})
export class SeatrowComponent {
  @Input() seatRow: PersistedSeatRow;
  @Input() showDeleteRowBtn = false;
  @Input() showAddRowBtn = false;

  @Output() seatCreationEvent = new EventEmitter<SeatCreationEvent>();
  @Output() seatRowDeletion = new EventEmitter<number>();
  @Output() seatRemoval = new EventEmitter<SeatRemovalPayload>();

  //references to typescript enums
  creationMenuDirectionEnum = CreationMenuDirection;
  seatTypeEnum = SeatType;

  //state variables
  showCreationMenuRight = false;
  showCreationMenuLeft = false;

  /**
   * Handles click outside of context menu
   *
   * @param from
   */
  handleContextMenuOutsideClick(from: CreationMenuDirection) {
    switch (from) {
      case CreationMenuDirection.left:
        this.showCreationMenuLeft = false;
        break;
      case CreationMenuDirection.right:
        this.showCreationMenuRight = false;
        break;
    }
  }

  handleSeatRemoval(id: number) {
    this.seatRemoval.emit({
      id,
      rowNr: this.seatRow.rowNr
    });
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
   *
   * @param direction
   * @param type
   */
  handleAddSeat(rowNr: number, direction: CreationMenuDirection, payload: SeatCreationEventPayload) {
    const { amount, type } = payload;

    this.seatCreationEvent.emit({ rowNr, direction, type, amountSeat: amount });

    this.closeActiveContextMenu();
  }
}
