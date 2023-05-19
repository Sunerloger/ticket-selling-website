import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {NewsService} from '../../../services/news.service';
import {AbbreviatedNews} from '../../../dtos/news';

@Component({
  selector: 'app-news',
  templateUrl: './news-overview.component.html',
  styleUrls: ['./news-overview.component.scss']
})
export class NewsOverviewComponent implements OnInit {
  pageIndex = 0;
  distance = 1;
  throttle = 2; // ms

  news: AbbreviatedNews[] = [];

  constructor(private authService: AuthService,
              private newsService: NewsService) {}

  ngOnInit() {
    this.newsService.getPage(this.pageIndex).subscribe((news: AbbreviatedNews[]) => {
      this.news = news;
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
