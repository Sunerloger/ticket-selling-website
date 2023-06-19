import {Component, OnInit} from '@angular/core';
import {Event} from '../../dtos/event';
import {EventService} from '../../services/event.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-event-chart',
  templateUrl: './event-chart.component.html',
  styleUrls: ['./event-chart.component.scss']
})
export class EventChartComponent implements OnInit{
  chart: any;
  chartOptions = {
    title: {
      text: 'Top 10 Events'
    },
    data: [{
      type: 'column',
      dataPoints: [

      ]
    }]
  };
  constructor(
    private eventService: EventService,
    private notification: ToastrService
  ) { }
  ngOnInit(): void {
  this.fetchTopEvents();
  }
  getChartInstance(chart: object) {
    this.chart = chart;
  }

  fetchTopEvents(): Promise<Event []> {
    return new Promise((resolve, reject) => {
      this.eventService.getTopEvents().subscribe({
        next: data => {
          console.log(data);
          let count = 0;
          for (const event of data) {
            if(event.soldTickets > 0){
              this.addDataPoint(event.title, count, event.soldTickets);
              count++;
            }
          }
          this.chart.render();
          resolve(data);
        },
        error: error => {
          console.error('Error fetching events', error);
          this.notification.error(`Could not fetch events. Errorcode: ${error.status}, Errortext: ${error.error.errors}`);
          reject(error);
        }
      });
    });
  }

  addDataPoint(label: string, count: number, value: number): void {
    const newDataPoint = { label,x: count, y: value };
    this.chartOptions.data[0].dataPoints.push(newDataPoint);
  }
}
