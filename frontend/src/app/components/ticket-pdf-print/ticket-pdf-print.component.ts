import { Component, ViewChild, ElementRef, AfterViewInit, OnInit } from '@angular/core';
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
  fonts = {
      // eslint-disable-next-line
      Roboto: {
      normal: 'https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.66/fonts/Roboto/Roboto-Regular.ttf',
      bold: `${window.location.origin}/assets/OleoScript-Regular.ttf`
      },
      // eslint-disable-next-line
      OleoScript: { 
        normal: `${window.location.origin}/assets/OleoScript-Regular.ttf`
      }

    };
  showPdfContent = false;
  url = '';
  ticket: TicketSeat = {
  ticketNr: 1,
  seat: {
    id: 1,
    price: 10.99,
    type: 'VIP',
    seatNr: 5,
    sectionName: 'Front Section',
    seatRowNr: 2,
  },
  event: {
    title: 'Concert',
    eventDatesLocation: [
      {
        date: new Date('2023-06-01'),
        city: 'City Name',
        areaCode: 'ABC',
        address: '123 Main St',
        room: 1,
        startingTime: '7:30 PM',
      },
    ],
    duration: '2 hours',
    category: 'Music',
    artist: 'John Doe',
    description: 'Enjoy an evening of live music with John Doe.',
    image: 'https://example.com/concert-image.jpg',
  },
};

  ngOnInit() {
     QRCode.toDataURL('amazon.com', (err, url) => {
      console.log(url);
      this.url = url;
    });

  }

  ngAfterViewInit() {
    //this.generateTicketPdf();
  }

  public generateTicketPdf() {
    //Retrieve pdf template code from component.html
    //const pdfContent = this.pdfContent.nativeElement.innerHTML;
    const pdfContent: any = `<div #pdfContent >
    <b style="font-size:36px; color:#23a6d5">Ticketline </b>
    <b> ------------------------------------------------------</b>
    <h2>${this.ticket.event.title}</h2>
    <p>
        <strong>Location:</strong> ${this.ticket.event.eventDatesLocation[0].city}
        |
        <strong>Area Code:</strong> ${this.ticket.event.eventDatesLocation[0].areaCode} |
        <strong>Address:</strong> ${this.ticket.event.eventDatesLocation[0].address} |
        <strong>Room:</strong> ${this.ticket.event.eventDatesLocation[0].room} |
        <strong>Starting Time:</strong> ${this.ticket.event.eventDatesLocation[0].startingTime}
    </p>
    <p>
        <strong>Duration:</strong> ${this.ticket.event.duration} |
        <strong>Category:</strong> ${this.ticket.event.category}
    </p>
    <p><strong>Artist:</strong> ${this.ticket.event.artist}</p>

    <h3>Ticket Details</h3>
    <p><strong>Ticket Number:</strong> ${this.ticket.ticketNr}</p>
    <p>
        <strong>Seat Type:</strong> ${this.ticket.seat.type} |
        <strong>Seat Number:</strong> ${this.ticket.seat.seatNr} |
        <strong>Section:</strong> ${this.ticket.seat.sectionName} |
        <strong>Row Number:</strong> ${this.ticket.seat.seatRowNr}
    </p>

</div>`;
    //Create documentDefinition for content


    fetch('url')
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
    pdfMake.createPdf(doc,null, this.fonts, vfs).download('ticket.pdf');

  }
}


