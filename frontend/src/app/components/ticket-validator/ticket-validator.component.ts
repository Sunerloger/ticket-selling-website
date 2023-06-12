import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { TicketPayload } from 'src/app/dtos/ticketPayload';
import { TicketValidatorService } from 'src/app/services/ticketvalidator.service';

@Component({
  selector: 'app-ticket-validator',
  templateUrl: './ticket-validator.component.html',
  styleUrls: ['./ticket-validator.component.scss']
})
export class TicketValidatorComponent implements OnInit {
  parameterValue: string;
  validationResult: string;
  constructor(private route: ActivatedRoute, private ticketService: TicketValidatorService, private notification: ToastrService) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.parameterValue = params['payload']; 
      
      const observable: Observable<TicketPayload> = this.ticketService.validatePayload(this.parameterValue);
      observable.subscribe({
      next: data => {
       this.validationResult = data.message;
      }, error: error => {
        this.notification.error(`Something went wrong... please try again!`);
      }
    });
    });
  }

  validityCheck(): boolean {
    return (this.validationResult === 'Ticket is valid!') ? true : false;
  }
}
