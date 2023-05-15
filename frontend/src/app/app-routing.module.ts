import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/message/message.component';
import {EventsComponent} from './components/events/events.component';
import {RegisterComponent} from './components/register/register.component';
import {NewsCreateComponent} from './components/news/news-create/news-create.component';
import {NewsComponent} from './components/news/news.component';
import {RouteGuard} from './guards/route.guard';

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'events', component: EventsComponent},
  {path: 'news', canActivate: [AuthGuard], component: NewsComponent},
  {path: 'news/create', canActivate: [RouteGuard], component: NewsCreateComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
