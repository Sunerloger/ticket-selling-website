import {Component, OnInit} from '@angular/core';
import {EventService} from '../../services/event.service';
import {ToastrService} from 'ngx-toastr';
import {AbbreviatedEvent} from '../../dtos/abbreviatedEvents';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-event-overview',
  templateUrl: './event-overview.component.html',
  styleUrls: ['./event-overview.component.scss']
})
export class EventOverviewComponent implements OnInit{
  events: AbbreviatedEvent[] = [];
  pageIndex = 0;
  distance = 1;
  throttle = 2;
  fromDate = '';
  toDate = '';
  artist = '';
  location = '';

  constructor(private service: EventService,
              private notification: ToastrService,
              private authService: AuthService) { }

  ngOnInit(): void {
    this.getEntities();
  }

  getEntities(): void {
    this.pageIndex = 0;
    const observable = this.service.getPage(this.pageIndex, this.fromDate, this.toDate, this.artist, this.location);
    observable.subscribe((data: AbbreviatedEvent[]) => {
        console.log(data);
        this.events = data;
        console.log(this.events);
      });
  }

  resetFilters(): void {
    this.pageIndex = 0;
    this.fromDate = '';
    this.toDate = '';
    this.artist = '';
    this.location = '';
    this.getEntities();
  }

  onScroll(): void {
    this.service.getPage(++this.pageIndex, this.fromDate, this.toDate, this.artist, this.location).subscribe((news: AbbreviatedEvent[]) => {
      console.log('GET page ' + this.pageIndex);
      this.events.push(...news);
    });
  }
}
