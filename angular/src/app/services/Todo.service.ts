import { Injectable } from '@angular/core';
import { TodoModel } from 'src/models/TodoModel';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  url: string = 'http://localhost:8090/api/todo';

  constructor(private http: HttpClient) {
  }

  countRemaining(): Observable<number> {
    return this.http.get<number>(`${this.url}/count-remaining-todos`);
  }

  getTodos(): Observable<TodoModel[]> {
    return this.http.get<TodoModel[]>(`${this.url}`);
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
    const updateUrl = `${this.url}/${todo.id}/edit`;
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
