import {Component, Input} from '@angular/core';
import {CartService} from '../../services/cart.service';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {ReservationService} from '../../services/reservation.service';
import {SeatDetail} from '../../dtos/seatDetail';
import {PersistedSeat} from '../../dtos/hallplan/hallplan';

@Component({
  selector: 'app-roomplan-cart',
  templateUrl: './roomplan-cart.component.html',
  styleUrls: ['./roomplan-cart.component.scss']
})

export class RoomplanCartComponent {
  @Input() items?: PersistedSeat[] = [];

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

  addToCart(seatList: PersistedSeat[]) {
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

  reserveSeats(seatList: PersistedSeat[]) {
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
