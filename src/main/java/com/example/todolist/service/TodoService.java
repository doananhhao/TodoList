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

    public List<Todo> getActiveTodos() {
        return this.todoRepository.getTodos(false);
    }

    public List<Todo> getCompletedTodos() {
        return this.todoRepository.getTodos(true);
    }

    public Todo addTodo(String title) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setCompleted(false);
        return this.todoRepository.addTodo(todo);
    }

    public Todo updateTodo(Todo todo) {
        return this.todoRepository.updateTodo(todo);
    }

    public boolean deleteTodo(int id) {
        return this.todoRepository.deleteTodo(id);
    }
}
