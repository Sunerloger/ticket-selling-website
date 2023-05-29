import {Component, OnInit} from '@angular/core';
import {CartItem} from '../../dtos/cartItem';
import {CartService} from '../../services/cart.service';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {CreatePurchase} from '../../dtos/purchases';
import {SeatDetail} from '../../dtos/seatDetail';

@Component({
  selector: 'app-cart-checkout',
  templateUrl: './cart-checkout.component.html',
  styleUrls: ['./cart-checkout.component.scss']
})
export class CartCheckoutComponent implements OnInit {

  items: CartItem[] = [];
  creationItem: CreatePurchase = {} as CreatePurchase;
  total = 0;
  withoutTaxes = 0;
  taxes = 0;

  constructor(private service: CartService,
              private notification: ToastrService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getItems();
    this.creationItem.useUserAddress = false;
    this.creationItem.address = '';
    this.creationItem.areaCode = 0;
    this.creationItem.city = '';
  }

  getItems() {
    const observable: Observable<CartItem[]> = this.service.getCart();
    observable.subscribe({
      next: data => {
        this.items = data;
      }, error: error => {
        this.router.navigate(['']);
      }
    });
  }

  formatTime(time: string): Date {
    const parts = time.split(':');
    const hours = Number(parts[0]);
    const minutes = Number(parts[1]);

    const date = new Date();
    date.setHours(hours);
    date.setMinutes(minutes);
    return date;
  }

  sumOfItems(): number {
    let sum = 0;
    this.items.forEach((element) => {
      sum += element.seat.price;
    });
    this.total = sum;
    this.taxes = sum * 0.2;
    this.withoutTaxes = this.total - this.taxes;
    return this.total;
  }

  purchase(): void {
    this.creationItem.seats = [];
    this.items.forEach((element) => {
      const seat: SeatDetail = {} as SeatDetail;
      seat.seatNr = element.seat.seatNr;
      seat.price = element.seat.price;
      seat.type = element.seat.type;
      seat.seatRowNr = element.seat.seatRowNr;
      seat.id = element.seat.id;
      this.creationItem.seats.push(seat);
    });
    this.service.purchaseCart(this.creationItem).subscribe(
      (response) => {
        console.log('Status:', response.status);
        this.notification.success(`Ticket successfully removed from cart.`);
      },
      (error) => {
        console.error('Error:', error);
        this.notification.error(`Something went wrong... please try again!`);
      }, () => {
        this.router.navigate(['/purchases']);
      }
    );
  }
}
