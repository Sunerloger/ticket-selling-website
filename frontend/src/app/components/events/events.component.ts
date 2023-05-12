import { Component, OnInit } from '@angular/core';
import {FormGroup, FormBuilder, Validators, FormControl, FormArray } from '@angular/forms';
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
    date: Date[100] = [new Date()],
    startTime: '',
    cityname: '',
    areaCode: '',
    duration: '',
    category: '',
    address: '',
    description: '',
    image: '',
  };
  today: string;
  eventForm: FormGroup;


  constructor(private fb: FormBuilder,
              private service: EventService,
              private notification: ToastrService) {
    this.today = new Date().toISOString().split('T')[0];
  }

  ngOnInit(): void {
    this.eventForm = this.fb.group({
      title: ['', Validators.required],
      date: [null, Validators.required],
      startTime: ['', Validators.required],
      cityname: ['', Validators.required],
      areaCode: ['', Validators.required],
      duration: ['', Validators.required],
      category: ['', Validators.required],
      address: ['', Validators.required],
      description: [''],
      image: ['']
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

  addDate() {
    this.event.date.push(new Date());
  }

  onSubmit(): void {
    this.eventForm.controls['title'].setValue(this.event.title);
    this.eventForm.controls['date'].setValue(this.event.date);
    this.eventForm.controls['startTime'].setValue(this.event.startTime);
    this.eventForm.controls['address'].setValue(this.event.address);
    this.eventForm.controls['image'].setValue(this.event.image);
    this.eventForm.controls['areaCode'].setValue(this.event.areaCode);
    this.eventForm.controls['duration'].setValue(this.event.duration);
    this.eventForm.controls['category'].setValue(this.event.category);
    this.eventForm.controls['description'].setValue(this.event.description);
    this.eventForm.controls['cityname'].setValue(this.event.cityname);
    console.log(this.eventForm);
    if (this.eventForm.valid) {
      console.log(this.event);
      const observable = this.service.create(this.event);
      observable.subscribe({
        next: data => {
          this.notification.success(`Event ${this.event.title} successfully created.`);
        },
        error: error => {
          console.error('Error creating event', error);
          this.notification.error(`Could not create a new event. Errorcode: ${error.status}, Errortext: ${error.error.errors}`);
        }
      });
    } else {
      // If the form is invalid, mark all fields as touched to display error messages
      this.eventForm.markAllAsTouched();
    }

  }
}
