import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './guards/auth.guard';
import {MessageComponent} from './components/message/message.component';
import {RoomplanCartComponent} from './components/roomplan-cart/roomplan-cart.component';
import {ShoppingCartComponent} from './components/shopping-cart/shopping-cart.component';
import {EventsComponent} from './components/events/events.component';
import {RegisterComponent} from './components/register/register.component';
import {NewsCreateComponent} from './components/news/news-create/news-create.component';
import {NewsOverviewComponent} from './components/news/news-overview/news-overview.component';
import {AdminRouteGuard} from './guards/admin-route.guard';
import {EditComponent} from './components/edit/edit.component';
import {NewsDetailComponent} from './components/news/news-detail/news-detail.component';
import {AdminBlockUnblockComponent} from './components/admin-block-unblock/admin-block-unblock.component';
import {RoomplaneditorComponent} from './components/roomplaneditor/roomplaneditor.component';
import {ReservationsComponent} from './components/reservations/reservations.component';
import {CartCheckoutComponent} from './components/cart-checkout/cart-checkout.component';
import {PurchasesComponent} from './components/purchases/purchases.component';
import {PurchaseDetailComponent} from './components/purchase-detail/purchase-detail.component';
import {ReservationCheckoutComponent} from './components/reservation-checkout/reservation-checkout.component';
import {EventOverviewComponent} from './components/event-overview/event-overview.component';
import {HallplanManagerComponent} from './components/hallplan-manager/hallplan-manager.component';
import {AdminManagerComponent} from './components/admin-manager/admin-manager.component';
import {
  PerformanceTicketSelctionComponent
} from './components/performance-ticket-selction/performance-ticket-selction.component';


const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'events', component: EventOverviewComponent},
  {path: 'login', component: LoginComponent},
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'roomplancart', component: RoomplanCartComponent},
  {path: 'cart', component: ShoppingCartComponent},
  {path: 'register', component: RegisterComponent},
  {
    path: 'admin', canActivate: [AdminRouteGuard], children: [
      {path: '', component: AdminManagerComponent},
      {path: 'block', canActivate: [AdminRouteGuard], component: AdminBlockUnblockComponent},
      {path: 'register', canActivate: [AdminRouteGuard], component: RegisterComponent},
    ]
  },
  {path: 'message', canActivate: [AuthGuard], component: MessageComponent},
  {path: 'news/create', canActivate: [AdminRouteGuard], component: NewsCreateComponent},
  {path: 'roomplan/:id/edit', component: RoomplaneditorComponent},
  {path: 'reservations', component: ReservationsComponent},
  {path: 'cart/checkout', component: CartCheckoutComponent},
  {path: 'purchases', component: PurchasesComponent},
  {path: 'purchases/:id', component: PurchaseDetailComponent},
  {path: 'reservations/:id/checkout', component: ReservationCheckoutComponent},
  {path: 'hallplans/manage', canActivate: [AuthGuard], component: HallplanManagerComponent},
  {path: 'events/create', canActivate: [AdminRouteGuard], component: EventsComponent},
  {path: 'events-overview', canActivate: [AuthGuard], component: EventOverviewComponent},
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
  {path: 'performance-tickets/:id', canActivate: [AuthGuard], component: PerformanceTicketSelctionComponent},
  {path: '**', redirectTo: 'news'},
  {path: 'hallplans/:id/edit', component: RoomplaneditorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
