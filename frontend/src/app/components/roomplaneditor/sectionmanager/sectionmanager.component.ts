import { Component, EventEmitter, Input, Output } from '@angular/core';
import { PersistedHallplan } from 'src/app/dtos/hallplan/hallplan';
import { CreateSectionPayload } from './create-section/create-section.component';

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
  @Input() roomplan: PersistedHallplan;
  @Output() createSectionEvent = new EventEmitter<CreateSectionPayload>();

  currentPage: SubmenuPage = SubmenuPage.addSection;
  submenuPageEnum = SubmenuPage;

  switchPage(navigateTo: SubmenuPage) {
    this.currentPage = navigateTo;
  }
}
