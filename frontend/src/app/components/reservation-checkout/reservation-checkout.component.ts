import {Component, OnInit} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CreatePurchase} from '../../dtos/purchases';
import {ToastrService} from 'ngx-toastr';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {SeatDetail} from '../../dtos/seatDetail';
import {ReservationService} from '../../services/reservation.service';
import {Reservation} from '../../dtos/reservation';

@Component({
  selector: 'app-reservation-checkout',
  templateUrl: './reservation-checkout.component.html',
  styleUrls: ['./reservation-checkout.component.scss']
})
export class ReservationCheckoutComponent implements OnInit {
  checkboxList: boolean[] = [];
  item: Reservation;
  creationItem: CreatePurchase = {} as CreatePurchase;

  constructor(private route: ActivatedRoute,
              private service: ReservationService,
              private notification: ToastrService,
              private router: Router) {
  }

  ngOnInit(): void {
    let reservationNr;
    this.route.params.subscribe(params => {
      reservationNr = params['id'];
    });
    this.getItem(reservationNr);
    this.creationItem.useUserAddress = false;
    this.creationItem.address = '';
    this.creationItem.areaCode = 0;
    this.creationItem.city = '';
  }

  getItem(reservationNr: number) {
    const observable: Observable<Reservation> = this.service.getReservation(reservationNr);
    observable.subscribe({
      next: data => {
        this.item = data;
        console.log(data);
        console.log(this.item);
        this.checkboxList = Array(this.item.reservedSeats.length).fill(true);
      }, error: error => {
        this.router.navigate(['']);
      }
    });
  }

  sumOfCheckedItems(): number {
    if (this.item === undefined){
      return 0;
    }
    let sum = 0;
    this.item.reservedSeats.forEach((element, index) => {
      if (this.checkboxList[index] === true){
        sum += element.price;
      }
    });
    return sum;
  }
  purchase(): void {
    this.creationItem.seats = [];
    this.item.reservedSeats.forEach((element, index) => {
      if (this.checkboxList[index] === true){
      const seat: SeatDetail = {} as SeatDetail;
      seat.seatNr = element.seatNr;
      seat.price = element.price;
      seat.type = element.type;
      seat.seatRowNr = element.seatRowNr;
      seat.id = element.id;
      this.creationItem.seats.push(seat);
      }
    });

    this.service.purchaseReservation(this.creationItem, this.item.reservationNr).subscribe(
      (response) => {
        console.log('Status:', response.status);
        this.notification.success(`Ticket successfully removed from cart.`);
      },
      (error) => {
        console.error('Error:', error);
        this.notification.error(`Something went wrong... please try again!`);
      }, () => {
        this.router.navigate(['/purchases']);
      }
    );

  }
}


