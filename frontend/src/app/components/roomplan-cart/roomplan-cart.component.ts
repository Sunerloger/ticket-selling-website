import {Component, Input} from '@angular/core';
import {RoomplanItem} from '../../dtos/roomplan';
import {CartService} from '../../services/cart.service';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';

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
    id: 6,
    status: 'FREE',
    type: 'STANDING_SEAT',
    capacity: 1,
    seatNr: 1,
    section: {
      id: 4,
      name: 'Back Section',
      color: 'blue',
      price: 40
    },
    seatrowId: 3
  };
  item2: RoomplanItem = {
    id: 1,
    status: 'FREE',
    type: 'SEAT',
    capacity: 1,
    seatNr: 1,
    section: {
      id: 1,
      name: 'VIP',
      color: 'gold',
      price: 100
    },
    seatrowId: 1
  };
  items = [this.item1, this.item2];

  constructor(private service: CartService,
              private notification: ToastrService,
              private router: Router) {
  }

  selectedSeats(): string {
    return this.items.length === 0
      ? 'no'
      : this.items.length.toString();
  }

  addToCart(seatList: RoomplanItem[]){
    //Todo: something with response
    this.service.addToCart(seatList).subscribe(
      (response) => {
        console.log('Status:', response.status);
        this.notification.success(`Ticket successfully removed from cart.`);
      },
      (error) => {
        console.error('Error:', error);
        this.notification.error(`Something went wrong... please try again!`);
      }, () => {
        this.router.navigate(['/cart']);
    }
    );

  }

}
