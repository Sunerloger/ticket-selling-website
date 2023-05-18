import { Component, Input } from '@angular/core';
import { PersistedRoomplan } from 'src/app/dtos/hallplan/roomplan';

enum SubmenuPage {
  addSection,
  manageSection
}

@Component({
  selector: 'app-sectionmanager',
  templateUrl: './sectionmanager.component.html',
  styleUrls: ['./sectionmanager.component.scss']
})
export class SectionmanagerComponent {
  @Input() roomplan: PersistedRoomplan;

  currentPage: SubmenuPage = SubmenuPage.addSection;
  submenuPageEnum = SubmenuPage;

  switchPage(navigateTo: SubmenuPage) {
    this.currentPage = navigateTo;
  }
}
