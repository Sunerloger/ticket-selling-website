import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import {FooterComponent} from './components/footer/footer.component';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {MessageComponent} from './components/message/message.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {httpInterceptorProviders} from './interceptors';
import { EventsComponent } from './components/events/events.component';
import { RegisterComponent } from './components/register/register.component';
import { NewsCreateComponent } from './components/news/news-create/news-create.component';
import { NewsComponent } from './components/news/news.component';
import {ToastrModule} from 'ngx-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatGridListModule} from '@angular/material/grid-list';
import {NgOptimizedImage} from '@angular/common';
import {MatInputModule} from '@angular/material/input';
import { RoomplaneditorComponent } from './components/roomplaneditor/roomplaneditor.component';
import { RoomplanvisualeditorComponent } from './components/roomplaneditor/roomplanvisualeditor/roomplanvisualeditor.component';
import { SeatrowComponent } from './components/roomplaneditor/seatrow/seatrow.component';
import { ContextmenuComponent } from './components/roomplaneditor/seatrow/contextmenu/contextmenu.component';
import { ToolbarComponent } from './components/roomplaneditor/toolbar/toolbar.component';
import { AddrowbtnComponent } from './components/roomplaneditor/seatrow/addrowbtn/addrowbtn.component';
import { SeatComponent } from './components/roomplaneditor/seatrow/seat/seat.component';
import { SectionmanagerComponent } from './components/roomplaneditor/sectionmanager/sectionmanager.component';
import { CreateSectionComponent } from './components/roomplaneditor/sectionmanager/create-section/create-section.component';
import { ManageSectionComponent } from './components/roomplaneditor/sectionmanager/manage-section/manage-section.component';
import { BsDatepickerModule } from 'ngx-bootstrap/datepicker';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    LoginComponent,
    MessageComponent,
    EventsComponent,
    RegisterComponent,
    NewsCreateComponent,
    NewsComponent,
    RoomplaneditorComponent,
    RoomplanvisualeditorComponent,
    SeatrowComponent,
    ContextmenuComponent,
    ToolbarComponent,
    AddrowbtnComponent,
    SeatComponent,
    SectionmanagerComponent,
    CreateSectionComponent,
    ManageSectionComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    ToastrModule.forRoot(),
    BrowserAnimationsModule,
    MatGridListModule,
    NgOptimizedImage,
    MatInputModule,
    BsDatepickerModule,
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
