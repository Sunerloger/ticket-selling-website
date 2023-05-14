import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';
import {News} from '../dtos/news';


@Injectable({
  providedIn: 'root'
})
export class NewsService {

  private newsBaseUri: string = this.globals.backendUri + '/news';

  constructor(private httpClient: HttpClient, private globals: Globals) {
  }


  /**
   * Persists news to the backend
   *
   * @param news to persist
   * @return an Observable for the created news
   */
  create(news: News): Observable<News> {
    return this.httpClient.post<News>(
      this.newsBaseUri,
      news,
    );

  }
}
