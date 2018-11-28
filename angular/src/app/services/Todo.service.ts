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

  getTodos(): Observable<TodoModel[]> {
    return this.http.get<TodoModel[]>(`${this.url}/all`);
  }

  add(title): Observable<TodoModel> {
    const addUrl = `${this.url}/${title}/add`;
    return this.http.get<TodoModel>(addUrl);
  }

  remove(id: number): Observable<Boolean> {
    const deleteUrl = `${this.url}/${id}/delete`;
    return this.http.delete<Boolean>(deleteUrl);
  }

  update(todo: TodoModel): Observable<TodoModel> {
    const updateUrl = `${this.url}/${todo.id}/edit`;
    return this.http.put<TodoModel>(updateUrl, todo);
  }

}
