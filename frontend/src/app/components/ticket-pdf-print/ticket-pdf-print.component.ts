import { Component, ViewChild, ElementRef, AfterViewInit, OnInit, Input } from '@angular/core';
import * as QRCode from 'qrcode';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import * as htmlToPdfMake from 'html-to-pdfmake';

import { TicketSeat } from 'src/app/dtos/ticket';

@Component({
  selector: 'app-ticket-pdf-print',
  templateUrl: './ticket-pdf-print.component.html',
  styleUrls: ['./ticket-pdf-print.component.scss']
})
export class TicketPdfPrintComponent implements AfterViewInit, OnInit {
  @ViewChild('pdfContent') pdfContent: ElementRef;

  @Input()
  ticket: TicketSeat;

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
  url = '';



  ngOnInit() {
     QRCode.toDataURL('website.com', (err, url) => {
      console.log(url);
      this.url = url;
    });

  }

  ngAfterViewInit() {
    //this.generateTicketPdf();
  }

  public generateTicketPdf() {
    //Retrieve pdf template code from component.html
    let seatType =  '';
    switch(this.ticket.seat.type) {
      case 'SEAT':
        seatType = 'Seat';
        break;
      case 'STANDING_SEAT':
        seatType = 'Standing Seat';
        break;

    }
    const pdfContent: any = `<div #pdfContent >
    <i style="font-size:48px; color:#23a6d5">Ticketline </i>
    <b> ------------------------------------------------------</b>
    <h2>${this.ticket.event.title}</h2>
    <p>
        <strong>Location:</strong> ${this.ticket.event.eventDatesLocation[0].city}
        |
        <strong>Area Code:</strong> ${this.ticket.event.eventDatesLocation[0].areaCode} |
        <strong>Address:</strong> ${this.ticket.event.eventDatesLocation[0].address} |
        <strong>Room:</strong> ${this.ticket.event.eventDatesLocation[0].room}
    </p>
    <p>
      <strong>Starting Time:</strong> ${this.ticket.event.eventDatesLocation[0].startingTime} |
      <strong>Duration:</strong> ${this.ticket.event.duration} |
      <strong>Category:</strong> ${this.ticket.event.category}
    </p>
    <p><strong>Artist:</strong> ${this.ticket.event.artist}</p>

    <h3 style="margin-bottom: 0px">Ticket Details</h3>
    <p style="margin-bottom: 5px"><strong>Ticket Number:</strong> ${this.ticket.ticketNr}</p>
    <p style="margin-top: 0px">
        <strong>Seat Type:</strong> ${seatType} |
        <strong>Seat Number:</strong> ${this.ticket.seat.seatNr} |
        <strong>Section:</strong> ${this.ticket.seat.sectionName} |
        <strong>Row Number:</strong> ${this.ticket.seat.seatRowNr}
    </p>

</div>`;
    //Create documentDefinition for content
    fetch(`${this.url}`)
    .then(response => response.blob())
    .then(blob => {
      const reader = new FileReader();
      reader.onloadend = () => {
        // Add the image to the document definition
        reader.readAsDataURL(blob);
        console.log(blob);
        const image = { image: reader.result };
        console.log(image);
        pdfContent.push(image);
      };
}
      );
      const documentDefinition = htmlToPdfMake(pdfContent);
    const doc = { content: [documentDefinition, {
      image: `${this.url}`
    } ]
    };

    //Set Font Dependencies
    const vfs = pdfFonts.pdfMake.vfs;
    pdfMake.createPdf(doc,null, this.fonts, vfs).download(`eTicket${this.ticket.ticketNr}.pdf`);

  }

  ticketPresent(): boolean {
    return this.ticket ? true : false;
  }
}


