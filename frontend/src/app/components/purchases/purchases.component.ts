import {Component, OnInit} from '@angular/core';
import {Purchase} from '../../dtos/purchases';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {PurchaseService} from '../../services/purchase.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-purchases',
  templateUrl: './purchases.component.html',
  styleUrls: ['./purchases.component.scss']
})
export class PurchasesComponent implements OnInit {
items: Purchase[] = [];
  constructor(private service: PurchaseService,
              private notification: ToastrService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getItems();
  }

  getItems(){
    const observable: Observable<Purchase[]> = this.service.getPurchases();
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

  sumOfItems(purchase: Purchase): number {
    let sum = 0;
    purchase.ticketList.forEach((element) =>{
      sum += element.seat.price;
    });
    return sum;
  }
}
