import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {PurchaseService} from '../../services/purchase.service';
import {ToastrService} from 'ngx-toastr';
import {Purchase} from '../../dtos/purchases';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-purchase-detail',
  templateUrl: './purchase-detail.component.html',
  styleUrls: ['./purchase-detail.component.scss']
})
export class PurchaseDetailComponent implements OnInit {
  item: Purchase;
  constructor(private route: ActivatedRoute,
              private service: PurchaseService,
              private notification: ToastrService,
              private router: Router) {
  }

  ngOnInit() {
    let purchaseNr;
    this.route.params.subscribe(params => {
      purchaseNr = params['id'];
    });
    const observable: Observable<Purchase> = this.service.getPurchase(purchaseNr);
    observable.subscribe({
      next: data => {
        this.item = data;
      }, error: error => {
        this.router.navigate(['']);
      }
    });
  }

  deletePurchase(purchaseNr: number){
    //Todo: something with response
    this.service.refundPurchase(purchaseNr).subscribe(
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

  sumOfItems(purchase: Purchase): number {
    let sum = 0;
    purchase.ticketList.forEach((element) =>{
      sum += element.seat.price;
    });
    return sum;
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

}
