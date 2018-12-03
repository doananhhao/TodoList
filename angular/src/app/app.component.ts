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

  remainingCount: number;
  completedCount: number;
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
    this.todoService.getTodos(this.currentStatus).subscribe((todos: TodoModel[]) => {
      this.todos = todos;
    })
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
    this.todoService.add(title).subscribe(
      (todo: any) => {
        this.todos.push(todo);
        this.updateCount();
      },
      (error: HttpErrorResponse) => alert(`This title is already exists`)
    );
  }

  remove(todo: TodoModel) {
    this.todoService.remove(todo.id).subscribe(
      () => {
        this.todos.splice(this.todos.indexOf(todo), 1);
        this.updateCount();
      },
      (error: HttpErrorResponse) => {
        alert(`This todo [${todo.title}] is not exists`);
      }
    );
  }

  update(todo: TodoModel) {
    this.todoService.update(todo).subscribe(
      (updatedTodo: TodoModel) => {
        if (updatedTodo !== null) {
          this.todos.forEach((currTodo) => {
            if (currTodo.id === updatedTodo.id) {
              currTodo = updatedTodo;
            }
          })
          this.updateCount();
        }
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        this.initTodos();
      }
    );
  }

  reorder(data) {
    this.todoService.reorder(data.todoId, data.newOrder).subscribe(
      (response) => {
        let todo = this.todos.filter((currentTodo) => currentTodo.id == data.todoId)[0];
        let currentIndex = this.todos.indexOf(todo);
        if (currentIndex < data.newOrder - 1) {
          this.reorderTodos(currentIndex, data.newOrder - 1);
        } else if (currentIndex > data.newOrder - 1) {
          this.reorderTodos(data.newOrder - 1, currentIndex, true);
        }
        this.changeTodoIndexInList(todo, data);
      },
      (error: HttpErrorResponse) => {
        alert(`Can not change order of this Todo!`);
      }
    );
  }

  private changeTodoIndexInList(todo: TodoModel, data: any) {
    this.todos.splice(this.todos.indexOf(todo), 1)[0];
    todo.order = data.newOrder;
    this.todos.splice(data.newOrder - 1, 0, todo);
  }

  private reorderTodos(from: number, to: number, isIncrease?: boolean) {
    this.todos.forEach((item, index) => {
      if (index >= from && index <= to) {
        if (isIncrease) {
          item.order = item.order + 1;
        } else {
          item.order = item.order - 1;
        }
      }
    });
  }

  private updateCount() {
    this.todoService.countRemaining().subscribe((count) => this.remainingCount = count);
    this.todoService.countCompleted().subscribe((count) => this.completedCount = count);
  }

  private get(isCompleted: boolean): TodoModel[] {
    return this.todos.filter((todo) => todo.completed == isCompleted);
  }

}
