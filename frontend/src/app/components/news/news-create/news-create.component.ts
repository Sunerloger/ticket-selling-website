import {Component, OnInit} from '@angular/core';
import {FormBuilder, NgForm} from '@angular/forms';
import {NewsService} from '../../../services/news/news.service';
import {News} from '../../../dtos/news';
import {Event} from '../../../dtos/event';
import {Observable} from 'rxjs';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {EventService} from '../../../services/event.service';


@Component({
  selector: 'app-news-create-component',
  templateUrl: './news-create.component.html',
  styleUrls: ['./news-create.component.scss']
})
export class NewsCreateComponent implements OnInit {

  news: News = {
    title: '',
    shortText: '',
    fullText: '',
    coverImage: null,
    images: [],
  };
  selectedFiles: File[];

  constructor(private fb: FormBuilder,
              private service: NewsService,
              private eventService: EventService,
              private notification: ToastrService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  onCoverImageSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.news.coverImage = reader.result as string;
      };
    }
  }

  onAdditionalImagesSelected(event: any) {
    this.selectedFiles = event.target.files;
    for (const file of this.selectedFiles) {
      this.convertToBase64(file);
    }
  }

  public onSubmit(form: NgForm): void {
    console.log('is form valid?', form.valid, this.news);

    if (form.valid) {

      if (this.news.title.trim().length === 0) {
        this.notification.warning('The title is blank', 'The form is not valid');
        return;
      }

      if (this.news.shortText.trim().length === 0) {
        this.notification.warning('The short description is blank', 'The form is not valid');
        return;
      }

      const observable: Observable<News> = this.service.create(this.news);

      observable.subscribe({
        next: () => {
          this.notification.success(`News ${this.news.title} successfully created.`);
          this.router.navigate(['/news']);
        },
        error: error => {
          console.error(`Error creating news`, error, this.news);
          this.notification.error('Could not create news. Errorcode: ' + error.status + ', Errortext: ' + error.error.errors);
        }
      });
    } else {
      this.notification.warning('Please fill out the required fields', 'The form is not valid');
    }
  }

  public removeCoverImage() {
    this.news.coverImage = null;
  }

  public removeImages(i: number) {
    this.news.images.splice(i,1);
  }

  /*public formatEventTitle(event: Event | null | undefined): string {
    return (event == null)
      ? ''
      : `${event.title}`;
  }

  eventSuggestions = (input: string): Observable<string[]> => (input === '')
    ? of([])
    : this.eventService.searchEventByName(input, 5);*/

  private convertToBase64(file: File) {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      const base64String = reader.result as string;
      this.news.images.push(base64String);
    };
    reader.onerror = (error) => {
      this.notification.error('Could not process Image');
      console.error('Error converting file to base64:', error);
    };
  }
}

