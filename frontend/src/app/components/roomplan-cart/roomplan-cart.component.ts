import {Component, Input} from '@angular/core';
import {RoomplanItem} from '../../dtos/roomplan';

@Component({
  selector: 'app-roomplan-cart',
  templateUrl: './roomplan-cart.component.html',
  styleUrls: ['./roomplan-cart.component.scss']
})

export class RoomplanCartComponent {
  /**
  @Input() items?: RoomplanItem[];
   */
  item1: RoomplanItem = {
    seatNr: 99,
    seatId: 2,
    rowNr: 10,
    section: {
     color: 'red',
     name: 'Block A',
      price: 11.50
    }
  };
  item2: RoomplanItem = {
    seatNr: 1,
    seatId: 2,
    rowNr: 3,
    section: {
      color: 'green',
      name: 'Block B',
      price: 20.10
    }
  };
  items = [this.item1, this.item2];
}
