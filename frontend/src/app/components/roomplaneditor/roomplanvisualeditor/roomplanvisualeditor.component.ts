import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PersistedHallplan } from 'src/app/dtos/hallplan/hallplan';
import { SeatCreationEvent, SeatRemovalPayload } from '../seatrow/seatrow.component';
import { ToolbarItem } from '../toolbar/toolbar.component';

@Component({
  selector: 'app-roomplanvisualeditor',
  templateUrl: './roomplanvisualeditor.component.html',
  styleUrls: ['./roomplanvisualeditor.component.scss']
})
export class RoomplanvisualeditorComponent {
  @Input() roomplan: PersistedHallplan;

  @Output() addRowEvent = new EventEmitter<{ rowNr: number }>();
  @Output() addSeatEvent = new EventEmitter<SeatCreationEvent>();
  @Output() seatRowDeletionEvent = new EventEmitter<number>();
  @Output() seatRemovalEvent = new EventEmitter<SeatRemovalPayload>();

  isDetailedView = false;

  handleToolbarItemClick(clickedItem: ToolbarItem) {
    if (clickedItem === ToolbarItem.detailedView) {
      this.isDetailedView = !this.isDetailedView;
    }
  }


  /**
   * Emits addRowEvent event with given rowNr.
   * When rowNr is omitted largest rowNr in roomplan incremented by one is used
   */
  handleAddRowEvent(rowNr?: number) {
    if (rowNr) {
      this.addRowEvent.emit({ rowNr });
    } else {
      let largestRowNr = 0;
      for (const seatrow of this.roomplan.seatRows) {
        if (seatrow.rowNr > largestRowNr) {
          largestRowNr = seatrow.rowNr;
        }
      }
      largestRowNr++;
      this.addRowEvent.emit({ rowNr: largestRowNr });
    }
  }

  /**
   * Emits onAddSeatEvent event with given event payload
   *
   * @param seatCreationEvent payload of event
   */
  onAddSeat(seatCreationEvent: SeatCreationEvent) {
    this.addSeatEvent.emit(seatCreationEvent);
  }
}
