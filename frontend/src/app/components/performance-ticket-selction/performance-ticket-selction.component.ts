import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {PersistedSeat} from '../../dtos/hallplan/hallplan';

@Component({
  selector: 'app-performance-ticket-selction',
  templateUrl: './performance-ticket-selction.component.html',
  styleUrls: ['./performance-ticket-selction.component.scss']
})
export class PerformanceTicketSelctionComponent implements OnInit{
  hallplanId: number;
  selectedSeats: PersistedSeat[] = [];
  constructor(private route: ActivatedRoute,
              private notification: ToastrService,
              private router: Router) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.hallplanId = params['id'];
    });
  }

  handleEvent(selectedSeats: PersistedSeat[]): void {
    this.selectedSeats = selectedSeats;
  }
}
