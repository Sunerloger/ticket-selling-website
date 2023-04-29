import { Component, EventEmitter, Output } from '@angular/core';
import { SeatType } from 'src/app/dtos/roomplan';


export enum CreationMenuDirection {
  LEFT,
  RIGHT
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

  //events
  @Output() seatCreationEvent = new EventEmitter<{ direction: CreationMenuDirection, type: SeatType }>()

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

  closeActiveContextMenu() {
    this.showCreationMenuLeft = false;
    this.showCreationMenuRight = false;
  }

  /**
   * Outputs seatCreationEvent when type is not vacant
   * @param direction 
   * @param type 
   */
  handleAddSeat(direction: CreationMenuDirection, type: SeatType) {
    console.log(direction, type);

    this.seatCreationEvent.emit({ direction, type });

    this.closeActiveContextMenu();
  }
}
