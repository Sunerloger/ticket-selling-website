import {Component, Input} from '@angular/core';
import {CartService} from '../../services/cart.service';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {ReservationService} from '../../services/reservation.service';
import {PersistedSeat} from '../../dtos/hallplan/hallplan';

@Component({
  selector: 'app-roomplan-cart',
  templateUrl: './roomplan-cart.component.html',
  styleUrls: ['./roomplan-cart.component.scss']
})

export class RoomplanCartComponent {
  @Input() items?: PersistedSeat[] = [];
  @Input() event?: number;
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
    this.cartService.addToCart(seatList).subscribe(
      (response) => {
        console.log('Status:', response.status);
        this.notification.success(`Ticket successfully removed from cart.`);
      },
      (error) => {
        const errorMessage = error.status === 0
          ? 'Server not reachable'
          : error.message.message;
        this.notification.error(errorMessage, 'Error:');
        console.error(error);
      }, () => {
        this.router.navigate(['/cart']);
      }
    );
  }

  reserveSeats(seatList: PersistedSeat[]) {
    this.reservationService.createReservation(seatList).subscribe(
      (response) => {
        console.log('Status:', response.status);
        this.notification.success(`Purchase successful.`);
      },
      (error) => {
        const errorMessage = error.status === 0
          ? 'Server not reachable'
          : error.message.message;
        this.notification.error(errorMessage, 'Error:');
        console.error(error);
      }, () => {
        this.router.navigate(['/reservations']);
      }
    );
  }

}
