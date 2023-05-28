import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Globals} from '../global/globals';
import {AbbreviatedNews, News} from '../dtos/news';


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


  /**
   * Loads a news-page from the backend
   *
   * @param pageIndex index of the page that should be fetched
   * @return an Observable for the fetched page of news entries
   */
  getPage(pageIndex: number): Observable<AbbreviatedNews[]> {
    let params: HttpParams = new HttpParams();
    params = params.set('pageIndex', pageIndex);
    return this.httpClient.get<AbbreviatedNews[]>(this.newsBaseUri, {params});
  }
}