import { Component, EventEmitter, Output } from '@angular/core';

export enum ToolbarItem {
  DETAILED_VIEW,
}

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent {
  ToolbarItem = ToolbarItem;

  //events
  @Output() toolbarClick = new EventEmitter<ToolbarItem>();

  handleToolbarItemClick(clickedItem: ToolbarItem) {
    console.log(clickedItem);
    this.toolbarClick.emit(clickedItem);
  }
}
