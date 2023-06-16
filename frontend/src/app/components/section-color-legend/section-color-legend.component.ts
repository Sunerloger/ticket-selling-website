import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {HallplanService} from '../../services/hallplan/hallplan.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {PersistedSection} from '../../dtos/hallplan/section';

@Component({
  selector: 'app-section-color-legend',
  templateUrl: './section-color-legend.component.html',
  styleUrls: ['./section-color-legend.component.scss']
})
export class SectionColorLegendComponent implements OnInit, OnChanges {
  @Input() hallplanId: number;

  sections: PersistedSection[] = [];
  // todo: remove sections without seats
  // todo: convert to detailed persisted section

  constructor(
    private service: HallplanService,
    private router: Router,
    private route: ActivatedRoute,
    private notification: ToastrService,
  ) {
  }

  ngOnInit(): void {
    this.initByHallplanId();
  }

  ngOnChanges(): void {
    this.fetchAllSections(this.hallplanId);
  }

  fetchAllSections(hallplanId: number) {
    this.service.getAllSections(hallplanId).subscribe({
      next: data => {
        // filter out unused sections:
        this.sections = data;
      },
      error: error => {
        const errorMessage = error.status === 0
          ? 'Server not reachable'
          : error.message.message;
        this.notification.error(errorMessage, 'Requested Hallplan does not exist');
      }
    });
  }

  initByHallplanId() {
    this.route.paramMap.subscribe(params => {
      const hallplanId = params.get('id');
      if (Number(hallplanId)) {
        this.fetchAllSections(Number(hallplanId));
      } else {
        this.router.navigate(['/events']);
        this.notification.error('Could not fetch hallplan', 'Hallplan with id "'+ hallplanId +'" does not exist');
      }
    });
  }

  formatPrice(price: number) {
    return price.toFixed(2).replace('.',',');
  }
}
