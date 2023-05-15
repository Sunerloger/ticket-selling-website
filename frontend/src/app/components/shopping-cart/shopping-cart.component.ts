import { Component } from '@angular/core';
import {CartItem} from '../../dtos/cartItem';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss']
})
export class ShoppingCartComponent {
  item1: CartItem = {
    seatNr: 99,
    seatId: 2,
    rowNr: 10,
    section: {
      color: 'red',
      name: 'Block A',
      price: 11.50
    },
    event: {
      name: 'Eventname - Lorem',
      date: '6/15/15, 2:03 PM',
    },
    reservation: false
  };
  item2: CartItem = {
    seatNr: 99,
    seatId: 2,
    rowNr: 10,
    section: {
      color: 'red',
      name: 'Block A',
      price: 11.50
    },
    event: {
      name: 'Eventname - Lorem',
      date: '6/15/15, 2:03 PM',
    },
    reservation: true
  };
  items = [this.item1, this.item2];
}
