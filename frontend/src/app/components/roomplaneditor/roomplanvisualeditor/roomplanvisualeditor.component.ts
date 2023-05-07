import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PersistedRoomplan } from 'src/app/dtos/roomplan';
import { SeatCreationEvent } from '../seatrow/seatrow.component';
import { ToolbarItem } from '../toolbar/toolbar.component';

@Component({
  selector: 'app-roomplanvisualeditor',
  templateUrl: './roomplanvisualeditor.component.html',
  styleUrls: ['./roomplanvisualeditor.component.scss']
})
export class RoomplanvisualeditorComponent {
  isDetailedView = false;

  //props
  @Input() roomplan: PersistedRoomplan;

  //events
  @Output() onAddRowEvent = new EventEmitter<{ rowNr: number }>();
  @Output() onAddSeatEvent = new EventEmitter<SeatCreationEvent>();
  @Output() onSeatRowDeletionEvent = new EventEmitter<number>();

  handleToolbarItemClick(clickedItem: ToolbarItem) {
    if (clickedItem === ToolbarItem.DETAILED_VIEW) {
      this.isDetailedView = !this.isDetailedView;
    }
  }


  /**
   * Emits addRowEvent event with given rowNr.
   * When rowNr is omitted largest rowNr in roomplan incremented by one is used
   */
  handleAddRow(rowNr?: number) {
    if (rowNr) {
      this.onAddRowEvent.emit({ rowNr: rowNr });
    } else {
      let largestRowNr = 0;
      for (const seatrow of this.roomplan.seatrows) {
        if (seatrow.rowNr > largestRowNr) {
          largestRowNr = seatrow.rowNr;
        }
      }
      largestRowNr++;
      this.onAddRowEvent.emit({ rowNr: largestRowNr });
    }
  }

  /**
   * Emits onAddSeatEvent event with given event payload
   * @param seatCreationEvent payload of event
   */
  onAddSeat(seatCreationEvent: SeatCreationEvent) {
    this.onAddSeatEvent.emit(seatCreationEvent);
  }
}
