import { Component } from '@angular/core';
import { TodoService } from './services/Todo.service';
import { TodoModel } from 'src/models/TodoModel';
import { StateTodo } from 'src/constant/StateTodo';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'todo-list';
  remainingCount: number;

  todos: TodoModel[];
  remainingTodos: TodoModel[];
  completedTodos: TodoModel[];
  currTodos: TodoModel[];

  currentStatus: string;

  constructor(private todoService: TodoService) {
  }

  ngOnInit(): void {
    this.currentStatus = StateTodo.ALL;
    this.getTodos();
  }

  getTodos() {
    this.todoService.getTodos().subscribe((response: any) => {
      this.todos = response["data"];
      this.getRemaining();
      this.getCompleted();
      this.calculateRemainingCount();
      this.currTodos = this.todos;
    })
  }

  getRemaining() {
    this.remainingTodos = this.get(false);
  }

  getCompleted() {
    this.completedTodos = this.get(true);
  }

  changeFilter(status: string) {
    this.currentStatus = status;
    if (status === StateTodo.ACTIVE) {
      this.setCurrentTodos(this.remainingTodos);
    } else if (status === StateTodo.COMPLETED) {
      this.setCurrentTodos(this.completedTodos);
    } else {
      this.setCurrentTodos(this.todos);
    }
  }

  add(title: String) {
    this.todoService.add(title).subscribe((todo: TodoModel) => {
      if (todo !== null) {
        this.todos.push(todo);
        this.remainingTodos.push(todo);
        this.calculateRemainingCount();
        this.changeFilter(this.currentStatus);
      }
    })
  }

  remove(todo: TodoModel) {
    this.todoService.remove(todo.id).subscribe((isDeleted: Boolean) => {
      if (isDeleted) {
        this.todos.splice(this.todos.indexOf(todo), 1);
        this.getRemaining();
        this.getCompleted();
        this.calculateRemainingCount();
        this.changeFilter(this.currentStatus);
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
        this.getRemaining();
        this.getCompleted();
        this.calculateRemainingCount();
        this.changeFilter(this.currentStatus);
      }
    })
  }

  private calculateRemainingCount() {
    this.remainingCount = this.remainingTodos.length;
  }

  private setCurrentTodos(todos: TodoModel[]) {
    this.currTodos = todos;
  }

  private get(isCompleted: boolean): TodoModel[] {
    return this.todos.filter((todo) => todo.completed == isCompleted);
  }

}
