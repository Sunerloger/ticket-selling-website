import { Component, OnInit  } from '@angular/core';
import { Hallplan } from 'src/app/dtos/hallplan/hallplan';
import { HallplanService } from 'src/app/services/hallplan/hallplan.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-hallplan-manager',
  templateUrl: './hallplan-manager.component.html',
  styleUrls: ['./hallplan-manager.component.scss']
})
export class HallplanManagerComponent implements OnInit {

  hallPlans: Hallplan[] = [];

  constructor(
    private hallPlanService: HallplanService,
    private router: Router,
    private route: ActivatedRoute,
    private notification: ToastrService,
  ) {
  }

  ngOnInit(): void {
    this.retrieveAllHallplans();
  }

  retrieveAllHallplans(): void {

      this.hallPlanService.getAllHallplans().subscribe({
      next: data => {
        this.hallPlans = data;
      },
      error: error => {
        const errorMessage = error.status === 0
          ? 'Server not reachable'
          : error.message.message;
        this.notification.error(errorMessage, 'Connection to server to retrieve all hallplans failed');
      }
    });

  }

}
