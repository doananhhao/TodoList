import { Injectable } from '@angular/core';
import { TodoModel } from 'src/models/TodoModel';
import { HttpClient } from '@angular/common/http';
import { StateTodo } from 'src/constant/StateTodo';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  url: string = '/api/todo';
  data = {
    filter: null
  };

  constructor(private http: HttpClient) {
  }

  getStatistics(): Observable<any> {
    return this.http.get<any>(`${this.url}/statistics`);
  }

  getTodos(status: String): Observable<TodoModel[]> {
    const getUrl = `${this.url}?status=${status}`;
    return this.http.get<TodoModel[]>(getUrl);
  }

  getLocalData(): any {
    return localStorage.getItem("data");
  }

  setLocalData() {
    localStorage.setItem("data", JSON.stringify(this.data));
  }

  setFilter(status: string) {
    this.data.filter = status;
    this.setLocalData();
  }

  getFilter(): string {
    const data = JSON.parse(this.getLocalData());
    if (data == null) {
      return StateTodo.ALL;
    }
    if (data.filter === StateTodo.ACTIVE) {
      return StateTodo.ACTIVE;
    } 
    if (data.filter === StateTodo.COMPLETED) {
      return StateTodo.COMPLETED;
    }
    return StateTodo.ALL;
  }

  add(title): Observable<TodoModel> {
    const addUrl = `${this.url}/add`;
    let data = {
      title: title
    };
    return this.http.post<any>(addUrl, data);
  }

  remove(id: number): Observable<Boolean> {
    const deleteUrl = `${this.url}/${id}/delete`;
    return this.http.delete<Boolean>(deleteUrl);
  }

  update(todo: TodoModel): Observable<TodoModel> {
    const updateUrl = `${this.url}/edit`;
    return this.http.put<TodoModel>(updateUrl, todo);
  }

  reorder(todoId: number, newOrder: number): Observable<any> {
    const reorderUrl = `${this.url}/change-order`;
    let data = {
      todoId: todoId,
      newOrder: newOrder
    };
    return this.http.put<any>(reorderUrl, data);
  }

}
