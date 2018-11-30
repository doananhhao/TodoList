import { Component } from '@angular/core';
import { TodoService } from './services/Todo.service';
import { TodoModel } from 'src/models/TodoModel';
import { StateTodo } from 'src/constant/StateTodo';

const SUCCESS = "success";
const RESULT = "result";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'todo-list';
  remainingCount: number;
  todos: TodoModel[];
  currentStatus: string;

  enableDrag: boolean;

  constructor(private todoService: TodoService) {
  }

  ngOnInit(): void {
    this.currentStatus = this.todoService.getFilter();
    this.enableDrag = true;
    this.initTodos();
    this.updateRemainingCount();
  }

  initTodos() {
    this.todoService.getTodos().subscribe((response: any) => {
      this.todos = response["data"];
    })
  }

  getCurrentTodos(status: string): TodoModel[] {
    this.currentStatus = status;
    this.enableDrag = false;
    if (status === StateTodo.ACTIVE) {
      return this.getRemaining();
    } else if (status === StateTodo.COMPLETED) {
      return this.getCompleted();
    }
    this.enableDrag = true;
    return this.todos;
  }

  changeFilter(status: string) {
    this.currentStatus = status;
    this.todoService.setFilter(status);
  }

  getRemaining() {
    return this.get(false);
  }

  getCompleted() {
    return this.get(true);
  }

  add(title: String) {
    this.todoService.add(title).subscribe((response: any) => {
      if (response[SUCCESS]) {
        this.todos.push(response[RESULT]);
        this.updateRemainingCount();
      } else {
        alert(response[RESULT]);
      }
    })
  }

  remove(todo: TodoModel) {
    this.todoService.remove(todo.id).subscribe((isDeleted: Boolean) => {
      if (isDeleted) {
        this.todos.splice(this.todos.indexOf(todo), 1);
        this.updateRemainingCount();
      }
    });
  }

  update(todo: TodoModel) {
    this.todoService.update(todo).subscribe((updatedTodo: TodoModel) => {
      if (updatedTodo !== null) {
        this.todos.forEach((currTodo) => {
          if (currTodo.id === updatedTodo.id) {
            currTodo = updatedTodo;
          }
        })
        this.updateRemainingCount();
      }
    })
  }

  reorder(data) {
    this.todoService.reorder(data.todoId, data.newOrder).subscribe((response) => {
      if (response[SUCCESS]) {
        let todo = this.todos.filter((currentTodo) => currentTodo.id == data.todoId)[0];
        let currentIndex = this.todos.indexOf(todo);
        if (currentIndex < data.newOrder - 1) {
          this.reorderTodos(currentIndex, data.newOrder - 1);
        } else if (currentIndex > data.newOrder - 1) {
          this.reorderTodos(data.newOrder - 1, currentIndex, true);
        }
        this.changeTodoIndexInList(todo, data);
      } else {
        alert(response[RESULT]);
      }
    })
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

  private updateRemainingCount() {
    this.todoService.countRemaining().subscribe((count) => this.remainingCount = count);
  }

  private get(isCompleted: boolean): TodoModel[] {
    return this.todos.filter((todo) => todo.completed == isCompleted);
  }

}
