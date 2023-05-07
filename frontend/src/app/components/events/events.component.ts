import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import {Event} from 'src/app/dtos/event';
import {EventService} from '../../services/event.service';
import {ToastrService} from 'ngx-toastr';
@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit{
  event: Event = {
    title: '',
    date: new Date(),
    startTime: new Date(),
    cityname: '',
    areaCode: 0,
    duration: 0,
    category: '',
    address: '',
    description: '',
    image: '',
  };
  eventForm: FormGroup;


  constructor(private fb: FormBuilder,
              private service: EventService,
              private notification: ToastrService) { }

  ngOnInit(): void {
    this.eventForm = this.fb.group({
      title: ['', Validators.required],
      date: ['', Validators.required],
      startingTime: ['', Validators.required],
      cityName: ['', Validators.required],
      areaCode: ['', Validators.required],
      duration: ['', Validators.required],
      category: ['', Validators.required],
      address: ['', Validators.required],
      description: ['', Validators.required]
    });
  }
  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.event.image = reader.result as string;
      };
    }
  }

  onSubmit() {
    console.log(this.event);
    const observable = this.service.create(this.event);
    observable.subscribe({
      next: data => {
        this.notification.success(`Event ${this.event.title} successfully created.`);
      },
      error: error => {
        console.error('Error creating event', error);
        this.notification.error('Could not create a new event. Errorcode: ' + error.status + ', Errortext: ' + error.error.errors);
      }
    });
  }
}
