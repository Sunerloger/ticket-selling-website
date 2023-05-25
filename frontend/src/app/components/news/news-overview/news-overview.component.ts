import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {NewsService} from '../../../services/news/news.service';
import {AbbreviatedNews} from '../../../dtos/news';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-news',
  templateUrl: './news-overview.component.html',
  styleUrls: ['./news-overview.component.scss']
})
export class NewsOverviewComponent implements OnInit {
  pageIndex = 0;
  distance = 1;
  throttle = 2; // ms
  showAlreadyReadNews = false;
  news: AbbreviatedNews[] = [];

  constructor(private authService: AuthService,
              private newsService: NewsService,
              private notification: ToastrService) {}

  ngOnInit() {
    this.initializeLoadedPages();
  }

  /**
   * Loads the first page of news from the backend and deletes all other pages from frontend
   */
  initializeLoadedPages() {
    this.pageIndex = 0;
    this.newsService.getPage(0).subscribe({
      next: (news: AbbreviatedNews[]) => {
        this.news = news;
      },
      error: error => {
        console.error('Error fetching news entries', error);
        const errorMessage = error.status === 0
          ? 'Is the backend up?'
          : error.message.message;
        this.notification.error(errorMessage, 'Could not fetch news entries');
      },
    });
  }

  /**
   * Returns true if the authenticated user is an admin
   */
  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  /**
   * Loads the next page of news from the backend
   */
  onScroll(): void {
    this.newsService.getPage(++this.pageIndex).subscribe((news: AbbreviatedNews[]) => {
      console.log('GET page ' + this.pageIndex);
      this.news.push(...news);
    });
  }

  dateOfCreationAsLocaleDate(entry: AbbreviatedNews): string {
    return new Date(entry.createdAt).toLocaleDateString();
  }

  timeOfCreationAsLocaleTime(entry: AbbreviatedNews): string {
    return new Date(entry.createdAt).toLocaleTimeString();
  }
}
