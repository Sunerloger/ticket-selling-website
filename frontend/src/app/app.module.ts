import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import {FooterComponent} from './components/footer/footer.component';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {MessageComponent} from './components/message/message.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {httpInterceptorProviders} from './interceptors';
import {EventsComponent} from './components/events/events.component';
import {RegisterComponent} from './components/register/register.component';
import {NewsCreateComponent} from './components/news/news-create/news-create.component';
import {NewsOverviewComponent} from './components/news/news-overview/news-overview.component';
import {ToastrModule} from 'ngx-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgOptimizedImage} from '@angular/common';
import {InfiniteScrollModule} from 'ngx-infinite-scroll';

import {EditComponent} from './components/edit/edit.component';

import { NewsDetailComponent } from './components/news/news-detail/news-detail.component';
import { TextareaAutoresizeDirective } from './directives/textarea-autoresize.directive';
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
import { EditableSectionComponent }
  from './components/roomplaneditor/sectionmanager/manage-section/editable-section/editable-section.component';
import { EventOverviewComponent } from './components/event-overview/event-overview.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import {AdminBlockUnblockComponent} from './components/admin-block-unblock/admin-block-unblock.component';
import { HallplanManagerComponent } from './components/hallplan-manager/hallplan-manager.component';
import { CustomerhallplandisplayComponent } from './components/customerhallplandisplay/customerhallplandisplay.component';
import { ImmutableseatComponent } from './components/roomplaneditor/seatrow/immutableseat/immutableseat.component';

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
    NewsOverviewComponent,
    NewsDetailComponent,
    TextareaAutoresizeDirective,
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
    EditComponent,
    EditableSectionComponent,
    AdminBlockUnblockComponent,
    EventOverviewComponent,
    HallplanManagerComponent,
    CustomerhallplandisplayComponent,
    ImmutableseatComponent,
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
    NgOptimizedImage,
    InfiniteScrollModule,
    BsDatepickerModule,
    MatPaginatorModule,
    InfiniteScrollModule,
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
