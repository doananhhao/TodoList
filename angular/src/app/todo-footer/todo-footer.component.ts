import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { StateTodo } from 'src/constant/StateTodo';

@Component({
  selector: 'app-todo-footer',
  templateUrl: './todo-footer.component.html',
  styleUrls: ['./todo-footer.component.css']
})
export class TodoFooterComponent implements OnInit {

  @Input() remainingCount: number;

  @Output() changeFilter = new EventEmitter();

  currentStatus: string;
  anyStatus: string;
  activeStatus: string;
  completedStatus: string;

  constructor() {
    this.anyStatus = StateTodo.ALL;
    this.activeStatus = StateTodo.ACTIVE;
    this.completedStatus = StateTodo.COMPLETED;

    this.currentStatus = this.anyStatus;
  }

  ngOnInit() {
  }

  setCurrentStatus(status: string) {
    this.currentStatus = status;
    this.changeFilter.next(this.currentStatus);
  }

}
