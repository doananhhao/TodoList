import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { TodoListComponent } from './todo-list/todo-list.component';
import { TodoItemComponent } from './todo-item/todo-item.component';
import { TodoHeaderComponent } from './todo-header/todo-header.component';
import { TodoFooterComponent } from './todo-footer/todo-footer.component';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { TodoModel } from 'src/models/TodoModel';
import { TodoService } from './services/Todo.service';

const routes: Routes = [
   { path: '', component: TodoListComponent, pathMatch: 'full' },
   { path: ':status', component: TodoListComponent }
];

@NgModule({
   declarations: [
      AppComponent,
      TodoListComponent,
      TodoItemComponent,
      TodoHeaderComponent,
      TodoFooterComponent
   ],
   imports: [
      BrowserModule,
      FormsModule,
      HttpClientModule,
      RouterModule.forRoot(routes)
   ],
   providers: [],
   bootstrap: [
      AppComponent
   ]
})
export class AppModule {
}
