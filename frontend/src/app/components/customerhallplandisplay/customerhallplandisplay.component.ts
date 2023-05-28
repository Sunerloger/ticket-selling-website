import { Component, Input, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { PersistedHallplan, PersistedSeat } from 'src/app/dtos/hallplan/hallplan';
import { HallplanService } from 'src/app/services/hallplan/hallplan.service';

export interface SeatSelectionPayload{
  selected: PersistedSeat[]
}

@Component({
  selector: 'app-customerhallplandisplay',
  templateUrl: './customerhallplandisplay.component.html',
  styleUrls: ['./customerhallplandisplay.component.scss']
})
export class CustomerhallplandisplayComponent implements OnInit {
  @Input() hallplanId: number;

  roomplan: PersistedHallplan = {
    id: 0,
    seatRows: [],
    name: '',
    description: ''
  };

  constructor(
    private service: HallplanService,
    private notification: ToastrService,
  ) {
  }

  ngOnInit(): void {
    this.fetchHallplanWithId(this.hallplanId);
  }

  fetchHallplanWithId(id: number){
    this.service.getHallplanById(id).subscribe({
      next: data => {
        this.roomplan = data;
      },
      error: error => {
        const errorMessage = error.status === 0
          ? 'Server not reachable'
          : error.message.message;
        this.notification.error(errorMessage, 'Requested Hallplan does not exist');
        console.error(error);
      }
    });
  }
}
