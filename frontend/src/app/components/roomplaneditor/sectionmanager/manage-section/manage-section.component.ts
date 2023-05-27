import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { PersistedSection, RESERVED_DEFAULT_SECTION_NAME } from 'src/app/dtos/hallplan/section';
import { HallplanService } from 'src/app/services/hallplan/hallplan.service';

@Component({
  selector: 'app-manage-section',
  templateUrl: './manage-section.component.html',
  styleUrls: ['./manage-section.component.scss']
})
export class ManageSectionComponent implements OnInit{
  @Input() sections: PersistedSection[];

  @Output() deleteSectionEvent = new EventEmitter<PersistedSection['id']>();


  constructor(
    private service: HallplanService,
    private notification: ToastrService,
    private router: Router,
    private route: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {

  }

  isDefaultSection(section: PersistedSection){
    return section.name === RESERVED_DEFAULT_SECTION_NAME;
  }



}
