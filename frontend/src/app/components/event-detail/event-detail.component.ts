import {Component, OnInit} from '@angular/core';
import {EventDate} from '../../dtos/eventDate';
import {Event} from '../../dtos/event';
import {ActivatedRoute} from '@angular/router';
import {EventService} from '../../services/event.service';
import {HallplanService} from '../../services/hallplan/hallplan.service';
import {ToastrService} from 'ngx-toastr';
import {concatMap, from, Observable} from 'rxjs';
import {AbbreviatedHallplan} from '../../dtos/hallplan/abbreviatedHallplan';

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.scss']
})
export class EventDetailComponent implements OnInit{

  eventDate: EventDate = {
    date: new Date(),
    city: '',
    areaCode: '',
    address: '',
    room: 1,
    startingTime: '',
  };
  event: Event = {
    title: '',
    eventDatesLocation: EventDate[100] = [this.eventDate],
    duration: '',
    category: '',
    artist: '',
    description: '',
    image: '',
  };
  hallPlanNames: Record<number, string>[] = [];
  constructor(
    private route: ActivatedRoute,
    private eventService: EventService,
    private hallplanService: HallplanService,
    private notification: ToastrService
  ) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.event.id = parseInt(params.get('id'), 10);
      this.executeRequests();
    });
  }
  getData(): Observable<Event> {
    return this.eventService.getById(this.event.id);
  }

// Second service method
  getOtherData(eventDate: EventDate): Observable<AbbreviatedHallplan> {
    // Make the HTTP request with data and return the Observable
    return this.hallplanService.getByIdAbbreviated(eventDate.room);
  }

// In your component or service where you want to call these methods
  executeRequests(): void {
    this.getData().pipe(
      concatMap((data: Event) => {
        this.event = data; // Assign the data to the global variable
        return from(data.eventDatesLocation).pipe(
          concatMap((item: any) => this.getOtherData(item))
        );
      })
    ).subscribe((result: any) => {
      const rec = { key: result.id, value: result.name };
      this.hallPlanNames.push(rec);
    });
  }

  fetchEventDetails() {
    const observable = this.eventService.getById(this.event.id);
    observable.subscribe({
      next: data => {
        this.event = data;;
      },
      error: error => {
        console.error('Error fetching event', error);
        this.notification.error(`Could not fetch this event. Errorcode: ${error.status}, Errortext: ${error.error.errors}`);
      }
    });
  }

  fetchHallplanName(id: number){
    const observable = this.hallplanService.getByIdAbbreviated(id);
    observable.subscribe({
      next: data => {
        const rec = { key: id, value: data.name };
        this.hallPlanNames.push(rec);
      },
      error: error => {
        console.error('Error fetching hallplans', error);
        this.notification.error(`Could not fetch hallplans. Errorcode: ${error.status}, Errortext: ${error.error.errors}`);
      }
    });
  }

  transformDuration(timeString: string): string {
    const [hours, minutes] = timeString.split(':');
    return `${parseInt(hours, 10)}h ${parseInt(minutes, 10)}m`;
  }

  transformTime(timeString: string): string {
    const [hours, minutes] = timeString.split(':');
    return `${parseInt(hours, 10)}:${parseInt(minutes, 10)} (CET)`;
  }

  getValueForKey(key: number): string | undefined {
    for (const record of this.hallPlanNames) {
      if (key in record) {
        return record[key];
      }
    }
    return undefined;
  }
}
