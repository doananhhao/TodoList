import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-todo-header',
  templateUrl: './todo-header.component.html',
  styleUrls: ['./todo-header.component.css']
})
export class TodoHeaderComponent implements OnInit {

  newTodo: string = "";

  @Output() addTodo = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  add() {
		if (this.newTodo.trim().length) {
			this.addTodo.next(this.newTodo);
      this.newTodo = '';
    }
	}

}
