package com.example.todolist.repository;

import com.example.todolist.models.Todo;

import java.util.List;

public interface TodoRepository {

    public List<Todo> getTodos(Boolean status);

    public Todo addTodo(Todo todo);

    public Todo updateTodo(Todo todo);

    public boolean deleteTodo(int id);

}
