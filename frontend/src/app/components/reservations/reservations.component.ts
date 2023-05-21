import {Component, OnInit} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {Reservation} from '../../dtos/reservation';
import {ReservationService} from '../../services/reservation.service';
import {Observable} from 'rxjs';
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.scss']
})
export class ReservationsComponent implements OnInit{
  items: Reservation[] = [];
  constructor(private service: ReservationService,
              private notification: ToastrService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getItems();
  }

  getItems(){
    const observable: Observable<Reservation[]> = this.service.getReservations();
    observable.subscribe({
      next: data => {
        this.items = data;
      }, error: error => {
        this.router.navigate(['']);
      }
    });
  }

  cancelReservation(reservation: Reservation){
    const observable: Observable<HttpResponse<any>> = this.service.deleteReservation(reservation.reservationNr);
    observable.subscribe({
      next: data => {
        //TODO:error message
        this.getItems();
      }, error: error => {
        //TODO:error message
      }
    });
  }

  formatTime(time: string): Date {
    const parts = time.split(':');
    const hours = Number(parts[0]);
    const minutes = Number(parts[1]);

    const date = new Date();
    date.setHours(hours);
    date.setMinutes(minutes);
    return date;
  }
}
