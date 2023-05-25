import { Injectable } from '@angular/core';
import {
  Resolve,
  ActivatedRouteSnapshot, Router
} from '@angular/router';
import { catchError, EMPTY, Observable} from 'rxjs';
import { News } from '../../dtos/news';
import { NewsService } from '../../services/news/news.service';
import {ToastrService} from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class NewsResolver implements Resolve<News> {
  constructor(private newsService: NewsService, private router: Router, private notification: ToastrService) {
  }
  resolve(route: ActivatedRouteSnapshot): Observable<News> {
    console.log('Resolve Route');
    const news = this.newsService.getById(route.params?.id).pipe(
      catchError(() => {
        console.error('Could not load news');
        this.notification.error('Maybe the requested news does not exist?', 'Could not load news');
        this.router.navigate(['news']);
        return EMPTY;
      })
    );
    console.log(news);
    return news;
  }
}
