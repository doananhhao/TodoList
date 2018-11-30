package com.example.todolist.service;

import com.example.todolist.models.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

  @Autowired
  private TodoRepository todoRepository;

  public List<Todo> getTodos() {
    return this.todoRepository.getTodos(null);
  }

  public Todo addTodo(Todo todo) {
    if (todo.getTitle().equalsIgnoreCase("")) {
      return null;
    }
    if (this.todoRepository.findByTitle(todo.getTitle()) != null) {
      return null;
    }
    todo.setOrder(this.todoRepository.getMaxOrder() + 1);
    return this.todoRepository.addTodo(todo);
  }

  public Todo updateTodo(Todo todo) {
    return this.todoRepository.updateTodo(todo);
  }

  public boolean deleteTodo(int id) {
    return this.todoRepository.deleteTodo(id);
  }

  public int countRemaining() {
    return this.todoRepository.countTodos(false);
  }

  public boolean changeTodoOrder(int id, int newOrder) {
    if (!isValidOrder(newOrder)) {
      return false;
    }
    Todo todo = this.todoRepository.findById(id);
    if (todo != null && isDifferentOrder(todo, newOrder)) {
      boolean isIncreaseOrder;
      boolean isSuccess;

      if (todo.getOrder() > newOrder) {
        isIncreaseOrder = true;
        isSuccess = this.todoRepository.changeOrderTodos(newOrder, todo.getOrder(), isIncreaseOrder);
      } else {
        isIncreaseOrder = false;
        isSuccess = this.todoRepository.changeOrderTodos(todo.getOrder(), newOrder, isIncreaseOrder);
      }

      if (isSuccess) {
        todo.setOrder(newOrder);
        this.todoRepository.updateTodo(todo);
        return true;
      }

    } else if (todo != null && !isDifferentOrder(todo, newOrder)){
      return true;
    }
    return false;
  }

  private boolean isDifferentOrder(Todo todo, int newOrder) {
    return todo.getOrder() != newOrder;
  }

  private boolean isValidOrder(int order) {
    if (order <= 0) {
      return false;
    }
    return this.todoRepository.getMaxOrder() >= order;
  }
}
