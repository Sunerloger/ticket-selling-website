import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PersistedSection } from 'src/app/dtos/hallplan/section';
import { HallplanService } from 'src/app/services/hallplan/hallplan.service';

@Component({
  selector: 'app-manage-section',
  templateUrl: './manage-section.component.html',
  styleUrls: ['./manage-section.component.scss']
})
export class ManageSectionComponent implements OnInit{
  @Input() sections: PersistedSection[];


  constructor(
    private service: HallplanService,
    private notification: ToastrService,
    private router: Router,
    private route: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {

  }




}
