import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit{
  event = {
    title: '',
    date: '',
    startTime: '',
    cityname: '',
    areaCode: '',
    duration: 0,
    category: '',
    address: '',
    description: ''
  };
  base64Image = '';
  eventForm: FormGroup;

  constructor(private fb: FormBuilder) { }

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
    console.log(this.eventForm.value);
    console.log(this.base64Image);
  }
}
