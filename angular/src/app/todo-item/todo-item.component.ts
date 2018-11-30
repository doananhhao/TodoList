import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TodoModel } from 'src/models/TodoModel';

@Component({
	selector: 'app-todo-item',
	templateUrl: './todo-item.component.html',
	styleUrls: ['./todo-item.component.css']
})
export class TodoItemComponent implements OnInit {

	@Input() todo: TodoModel;
	@Input() enableDrag: boolean;

	@Output() itemModified = new EventEmitter();
	@Output() itemRemoved = new EventEmitter();
	@Output() itemReorder = new EventEmitter();

	editing: boolean = false;

	constructor() { }

	ngOnInit() {
	}

	cancelEditing() {
		this.editing = false;
	}

	stopEditing(editedTitle) {
		this.todo.title = editedTitle.value;
		this.editing = false;

		if (this.todo.title.length === 0) {
			this.remove();
		} else {
			this.update();
		}
	}

	edit() {
		this.editing = true;
	}

	toggleCompletion() {
		this.todo.completed = !this.todo.completed;
		this.update();
	}

	remove() {
		this.itemRemoved.next(this.todo);
	}

	update() {
		this.itemModified.next(this.todo);
	}

	onDrop(event) {
		event.preventDefault();
		let data = {
			todoId: event.dataTransfer.getData("text/plain"),
			newOrder: this.todo.order
		};
		this.itemReorder.next(data);
	}

	onDragStart(event) {
		event.dataTransfer.setData("text/plain", this.todo.id);
		event.dropEffect = "move";
	}

	onDragOver(event) {
		event.preventDefault();
		event.dataTransfer.dropEffect = "move";
	}

}
