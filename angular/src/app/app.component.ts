import { Component } from '@angular/core';
import { TodoService } from './services/Todo.service';
import { TodoModel } from 'src/models/TodoModel';
import { StateTodo } from 'src/constant/StateTodo';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  statistics = {
    remaining: {
      count: 0
    }
  };
  todos: TodoModel[];
  currentStatus: string;

  enableDrag: boolean;

  constructor(private todoService: TodoService) {
  }

  ngOnInit(): void {
    this.currentStatus = this.todoService.getFilter();
    this.enableDrag = true;
    this.initTodos();
    this.updateCount();
  }

  initTodos() {
    this.todoService.getTodos(this.currentStatus).toPromise()
      .then((todos) => this.todos = todos);
  }

  changeFilter(status: string) {
    this.currentStatus = status;
    if (status === StateTodo.ALL) {
      this.enableDrag = true;
    } else {
      this.enableDrag = false;
    }
    this.todoService.setFilter(status);
    this.initTodos();
  }

  getRemaining() {
    return this.get(false);
  }

  getCompleted() {
    return this.get(true);
  }

  add(title: String) {
    this.todoService.add(title).toPromise()
      .then((todo) => {
        this.updateCount();
        this.initTodos();
      }).catch((error: HttpErrorResponse) => alert(`This title is already exists`));
  }

  remove(todo: TodoModel) {
    this.todoService.remove(todo.id).toPromise()
      .then((todo) => {
        this.updateCount();
        this.initTodos();
      }).catch((error: HttpErrorResponse) => alert(`This todo [${todo.title}] is not exists`));
  }

  update(todo: TodoModel) {
    this.todoService.update(todo).toPromise()
      .then((updatedTodo) => {
        this.updateCount();
        this.initTodos();
      })
      .catch((error: HttpErrorResponse) => {
        this.initTodos();
        alert(`This title is already exists`);
      });
  }

  reorder(data) {
    this.todoService.reorder(data.todoId, data.newOrder).toPromise()
      .then((response) => {
        this.initTodos();
      })
      .catch((error: HttpErrorResponse) => {
        alert(`Can not change order of this Todo!`);
      });
  }

  private updateCount() {
    this.todoService.getStatistics().toPromise()
      .then((data) => this.statistics = data)
      .catch((error) => {
        this.statistics = {
          remaining: {
            count: 0
          }
        }
      });
  }

  private get(isCompleted: boolean): TodoModel[] {
    return this.todos.filter((todo) => todo.completed == isCompleted);
  }

}
