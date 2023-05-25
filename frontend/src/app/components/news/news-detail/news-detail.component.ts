import {Component, OnInit} from '@angular/core';
import {NewsService} from '../../../services/news/news.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {News} from '../../../dtos/news';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'app-news-detail',
  templateUrl: './news-detail.component.html',
  styleUrls: ['./news-detail.component.scss']
})
export class NewsDetailComponent implements OnInit {
  news: News = {
    title: 'wrong title',
    fullText: 'wrong full text',
    coverImage: null,
    images: [],
  };
  selectedIndex = 0;

  constructor(
    private service: NewsService,
    private router: Router,
    private route: ActivatedRoute,
    private notification: ToastrService,
    private authService: AuthService,
  ) {
  }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      if (data.news != null) {
        this.news = data.news;
        if (this.news.coverImage != null) {
          this.news.images.unshift(this.news.coverImage);
        }
      }
      // TODO error
    });
  }

  nextImage(): void {
    this.selectedIndex = (++this.selectedIndex) % this.news.images.length;
  }

  previousImage(): void {
    const listLength = this.news.images.length;
    this.selectedIndex = (((--this.selectedIndex) % listLength) + listLength) % listLength;
  }

  setImage(index: number): void {
    this.selectedIndex = index;
  }

  dateOfCreationAsLocaleDate(): string {
    return new Date(this.news.createdAt).toLocaleDateString();
  }

  timeOfCreationAsLocaleTime(): string {
    return new Date(this.news.createdAt).toLocaleTimeString();
  }

  /**
   * Returns true if the authenticated user is an admin
   */
  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  public onDelete(): void {
    const observable = this.service.deleteById(this.news.id);
    observable.subscribe({
      next: () => {
        this.notification.success(`News ${this.news.title} successfully deleted.`);
        this.router.navigate(['/news']);
      },
      error: error => {
        console.error(`Error deleting news`, error);
        const errorMessage = error.status === 0
          ? 'Is the backend up?'
          : error.message.message;
        this.notification.error(errorMessage, `Could not delete news`);
      }
    });
  }
}
