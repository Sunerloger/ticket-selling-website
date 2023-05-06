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
    description: ''
  };
  base64Image = '';
  eventForm: FormGroup;
  token = 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzZWN1cmUtYmFja2VuZCIsImF1ZCI6InNlY3VyZS1hcHAiLCJzdWIiOiJhZG1pbkBlbWFpbC5jb20iLCJleHAiOjE2ODM0Mjg5MTksInJvbCI6WyJST0xFX0FETUlOIiwiUk9MRV9VU0VSIl19.wqswe__snIL6UyR8XqjS2ifoT-dfL9RjbjTclRZZwe7Y9l3zaC08dt62wNFwfqegUO8K1FUGgcJPCSuYhm4wWw';

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
        this.base64Image = reader.result as string;
      };
    }
  }

  onSubmit() {
    console.log(this.event);
    console.log(this.base64Image);
    const observable = this.service.create(this.event, this.token);
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
