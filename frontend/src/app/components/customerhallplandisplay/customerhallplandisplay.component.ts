import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { PersistedHallplan, PersistedSeat } from 'src/app/dtos/hallplan/hallplan';
import { HallplanService } from 'src/app/services/hallplan/hallplan.service';
import { SeatSelectionPayload } from '../roomplaneditor/seatrow/immutableseat/immutableseat.component';

@Component({
  selector: 'app-customerhallplandisplay',
  templateUrl: './customerhallplandisplay.component.html',
  styleUrls: ['./customerhallplandisplay.component.scss']
})
export class CustomerhallplandisplayComponent implements OnInit {
  @Input() hallplanId: number;
  @Output() selectionChangeEvent = new EventEmitter<PersistedSeat[]>

  roomplan: PersistedHallplan = {
    id: 0,
    seatRows: [],
    name: '',
    description: ''
  };
  selectedSeats: Map<PersistedSeat["id"], PersistedSeat> = new Map();

  constructor(
    private service: HallplanService,
    private notification: ToastrService,
  ) {
  }

  /**
   * Given SeatSelectionPayload is inserted or removed from internal selectedSeats map. 
   * Emits an seatSelectionChangeEvent with a list of all currently selected seats
   * @param payload 
   */
  handleSeatSelectionEvent(payload: SeatSelectionPayload) {
    const clonedSelectedSeats = new Map(this.selectedSeats);
    const seat = payload.seat;
    const { id } = seat;

    if (payload.isSelected) {
      clonedSelectedSeats.set(id, seat);
    } else {
      clonedSelectedSeats.delete(id);
    }
    this.selectedSeats = clonedSelectedSeats;
    this.selectionChangeEvent.emit(Array.from(this.selectedSeats.values()));
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
