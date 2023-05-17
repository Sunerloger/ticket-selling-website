import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Globals} from '../global/globals';
import {Observable} from 'rxjs';
import {CartItem} from '../dtos/cartItem';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartBaseUri: string = this.globals.backendUri + '/cart';
  constructor(private httpClient: HttpClient, private globals: Globals) {
  }

  getCart(): Observable<CartItem[]> {
    return this.httpClient.get<CartItem[]>(
      this.cartBaseUri
    );
  }


}
