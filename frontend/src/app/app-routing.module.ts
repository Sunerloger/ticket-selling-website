import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/message/message.component';
import {EventsComponent} from './components/events/events.component';
import {RegisterComponent} from './components/register/register.component';
import {NewsCreateComponent} from './components/news/news-create/news-create.component';
import {NewsOverviewComponent} from './components/news/news-overview/news-overview.component';
import {AdminRouteGuard} from './guards/admin-route.guard';
import {RoomplaneditorComponent} from './components/roomplaneditor/roomplaneditor.component';
import {EditComponent} from './components/edit/edit.component';
import {NewsDetailComponent} from './components/news/news-detail/news-detail.component';


const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'events', canActivate: [AdminRouteGuard], component: EventsComponent},
  {
    path: 'news', canActivate: [AuthGuard], children: [
      {path: '', component: NewsOverviewComponent},
      {
        path: ':id/info', component: NewsDetailComponent/*, resolve: {
        news: NewsResolver
      }*/
      },
      {path: 'create', canActivate: [AdminRouteGuard], component: NewsCreateComponent},
    ]
  },
  {path: 'edit', component: EditComponent},
  {path: 'roomplan/:id/edit', component: RoomplaneditorComponent},
  {path: 'hallplans/:id/edit', component: RoomplaneditorComponent},
  {path: '**', redirectTo: 'news'},
];


@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
