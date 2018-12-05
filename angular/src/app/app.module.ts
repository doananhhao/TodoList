import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { TodoListComponent } from './todo-list/todo-list.component';
import { TodoItemComponent } from './todo-item/todo-item.component';
import { TodoHeaderComponent } from './todo-header/todo-header.component';
import { TodoFooterComponent } from './todo-footer/todo-footer.component';
import { HttpClientModule } from '@angular/common/http';
import { ChartComponent } from './chart/chart.component';

@NgModule({
   declarations: [
      AppComponent,
      TodoListComponent,
      TodoItemComponent,
      TodoHeaderComponent,
      TodoFooterComponent,
      ChartComponent,
   ],
   imports: [
      BrowserModule,
      FormsModule,
      HttpClientModule,
   ],
   providers: [],
   bootstrap: [
      AppComponent
   ]
})
export class AppModule {
}
