import { Component, OnInit } from '@angular/core';
import {CartItem} from '../../dtos/cartItem';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {CartService} from '../../services/cart.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.scss']
})
export class ShoppingCartComponent implements OnInit{
  items: CartItem[] = [];
  constructor(private service: CartService,
              private notification: ToastrService,
              private router: Router) {
  }
  ngOnInit(): void {
    this.getItems();
  }

  getItems(){
    const observable: Observable<CartItem[]> = this.service.getCart();
    observable.subscribe({
      next: data => {
        this.items = data;
      }, error: error => {
        this.router.navigate(['/horses']);
      }
    });
  }

  delete(index: number){
    //Todo: remove the item in the backend
    this.items.splice(index,1);
    //Todo: then reload the cart
  }

}
