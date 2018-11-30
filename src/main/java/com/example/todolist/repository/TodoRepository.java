package com.example.todolist.repository;

import com.example.todolist.models.Todo;

import java.util.List;

public interface TodoRepository {

  public List<Todo> getTodos(Boolean status);

  public Todo addTodo(Todo todo);

  public Todo findByTitle(String title);

  public Todo findById(int id);

  public Todo updateTodo(Todo todo);

  public boolean deleteTodo(int id);

  public int countTodos(Boolean status);

  public boolean changeOrderTodos(int from, int to, boolean isIncreaseOrder);

  public int getMaxOrder();

}
