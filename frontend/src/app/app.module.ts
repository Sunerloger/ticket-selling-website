import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { MessageComponent } from './components/message/message.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { httpInterceptorProviders } from './interceptors';
import { RoomplaneditorComponent } from './components/roomplaneditor/roomplaneditor.component';
import { RoomplanvisualeditorComponent } from './components/roomplaneditor/roomplanvisualeditor/roomplanvisualeditor.component';
import { SeatrowComponent } from './components/roomplaneditor/seatrow/seatrow.component';
import { ContextmenuComponent } from './components/roomplaneditor/seatrow/contextmenu/contextmenu.component';
import { ToolbarComponent } from './components/roomplaneditor/toolbar/toolbar.component';
import { AddrowbtnComponent } from './components/roomplaneditor/seatrow/addrowbtn/addrowbtn.component';
import { SeatComponent } from './components/roomplaneditor/seatrow/seat/seat.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    MessageComponent,
    RoomplaneditorComponent,
    RoomplanvisualeditorComponent,
    SeatrowComponent,
    ContextmenuComponent,
    ToolbarComponent,
    AddrowbtnComponent,
    SeatComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
