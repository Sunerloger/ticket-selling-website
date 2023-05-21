import {Component, Input} from '@angular/core';
import {CartService} from '../../services/cart.service';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {TicketSeat} from '../../dtos/ticket';
import {ReservationService} from '../../services/reservation.service';

@Component({
  selector: 'app-roomplan-cart',
  templateUrl: './roomplan-cart.component.html',
  styleUrls: ['./roomplan-cart.component.scss']
})

export class RoomplanCartComponent {
  /**
  @Input() items?: RoomplanItem[];
   */
  item1: TicketSeat = {
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
  item2: TicketSeat = {
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

  constructor(private cartService: CartService,
              private reservationService: ReservationService,
              private notification: ToastrService,
              private router: Router) {
  }

  selectedSeats(): string {
    return this.items.length === 0
      ? 'no'
      : this.items.length.toString();
  }

  addToCart(seatList: TicketSeat[]){
    //Todo: something with response
    this.cartService.addToCart(seatList).subscribe(
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

  reserveSeats(seatList: TicketSeat[]){
    //Todo: something with response
    this.reservationService.createReservation(seatList).subscribe(
      (response) => {
        console.log('Status:', response.status);
        this.notification.success(`Ticket successfully removed from cart.`);
      },
      (error) => {
        console.error('Error:', error);
        this.notification.error(`Something went wrong... please try again!`);
      }, () => {
        this.router.navigate(['/reservations']);
      }
    );

  }


}
