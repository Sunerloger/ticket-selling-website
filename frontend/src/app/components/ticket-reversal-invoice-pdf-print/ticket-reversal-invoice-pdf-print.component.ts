import {AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import * as htmlToPdfMake from 'html-to-pdfmake';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import {Purchase} from 'src/app/dtos/purchases';

@Component({
  selector: 'app-ticket-reversal-invoice-pdf-print',
  templateUrl: './ticket-reversal-invoice-pdf-print.component.html',
  styleUrls: ['./ticket-reversal-invoice-pdf-print.component.scss']
})
export class TicketReversalInvoicePdfPrintComponent implements AfterViewInit, OnInit {
  @ViewChild('pdfContent') pdfContent: ElementRef;

  @Input()
  purchase: Purchase;

  fonts = {
    // eslint-disable-next-line
    Roboto: {
      normal: 'https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.66/fonts/Roboto/Roboto-Regular.ttf',
      bold: 'https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.66/fonts/Roboto/Roboto-Medium.ttf',
      italics: `${window.location.origin}/assets/OleoScript-Regular.ttf`
    },
    // eslint-disable-next-line
    OleoScript: {
      normal: `${window.location.origin}/assets/OleoScript-Regular.ttf`
    }
  };
  showPdfContent = false;

  ngOnInit() {
  }

  ngAfterViewInit() {
    //this.generateReversalInvoicePdf();
  }

  public generateReversalInvoicePdf() {
    // Create the PDF content dynamically
    const pdfContent: any = `
      <div #pdfContent>
        <i style="font-size: 48px; color: #23a6d5">Reversal Invoice</i>
        <b>------------------------------------------------------</b>
        <h2>Purchase Number: ${this.purchase.purchaseNr}</h2>
        <p>
          <strong>Purchase Date:</strong> ${this.purchase.purchaseDate}
          <br>
          <strong>Billing Address:</strong> ${this.purchase.billAddress}
          <br>
          <strong>Billing City:</strong> ${this.purchase.billCityName}
          <br>
          <strong>Billing Area Code:</strong> ${this.purchase.billAreaCode}
        </p>
        <h3 style="margin-bottom: 0px">Ticket List</h3>
        ${this.purchase.ticketList
      .map(
        ticket => `
          <p style="margin-bottom: 5px">
            <strong>TicketNr:</strong> ${ticket.ticketNr} |
            <strong>Seat:</strong> ${ticket.seat.seatNr}
          </p>
        `
      )
      .join('')}
        <h2>Total Refunded Price: ${this.sumOfItems(this.purchase)}€</h2>
      </div>
    `;

    // Create documentDefinition for content
    const documentDefinition = htmlToPdfMake(pdfContent);
    const doc = {
      content: [documentDefinition]
    };

    // Set font dependencies
    const vfs = pdfFonts.pdfMake.vfs;
    pdfMake.createPdf(doc, null, this.fonts, vfs).download('reversal_invoice.pdf');
  }

  purchasePresent(): boolean {
    return this.purchase ? true : false;
  }

  sumOfItems(purchase: Purchase): string {
    let sum = 0;
    purchase.ticketList.forEach((element) => {
      sum += element.seat.price;
    });
    return sum.toFixed(2).replace('.', ',');

  }
}
