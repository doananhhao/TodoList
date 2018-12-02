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

    public List<Todo> getTodos(String status) {
        if (status.equalsIgnoreCase("completed")) {
            return this.todoRepository.findAllByCompletedOrderByOrderAsc(true);
        }
        if (status.equalsIgnoreCase("active")) {
            return this.todoRepository.findAllByCompletedOrderByOrderAsc(false);
        }
        return this.todoRepository.findAllByOrderByOrderAsc();
    }

    public Todo addTodo(Todo todo) {
        if (todo.getTitle().equalsIgnoreCase("")) {
            return null;
        }
        if (this.todoRepository.findByTitle(todo.getTitle()) != null) {
            return null;
        }
        Todo maxOrderTodo = this.todoRepository.findFirstByTitleIsNotNullOrderByOrderDesc();
        todo.setOrder((maxOrderTodo != null ? maxOrderTodo.getOrder() : 0) + 1);
        return this.todoRepository.save(todo);
    }

    public Todo updateTodo(Todo todo) {
        if (this.todoRepository.findById(todo.getId()) == null) {
            return null;
        }
        return this.todoRepository.save(todo);
    }

    public boolean deleteTodo(int id) {
        Todo todo = this.todoRepository.findById(id);
        if (todo != null) {
            if (needToChangeOrderTodos(todo)) {
                int maxOrder = this.todoRepository.findFirstByTitleIsNotNullOrderByOrderDesc().getOrder();
                updateOrderTodos(todo.getOrder(), maxOrder, false);
            }
            this.todoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private boolean needToChangeOrderTodos(Todo todo) {
        int maxOrder = this.todoRepository.findFirstByTitleIsNotNullOrderByOrderDesc().getOrder();
        return maxOrder != todo.getOrder();
    }

    private void updateOrderTodos(int from, int to, boolean isIncrease) {
        this.todoRepository.findByOrderBetween(from, to)
                .forEach((curTodo) -> {
                    if (isIncrease) {
                        curTodo.setOrder(curTodo.getOrder() + 1);
                    } else {
                        curTodo.setOrder(curTodo.getOrder() - 1);
                    }
                    this.todoRepository.save(curTodo);
                });
    }

    public int countRemaining() {
        return this.todoRepository.countAllByCompleted(false);
    }

    public boolean changeOrderTodo(int id, int newOrder) {
        if (!isValidOrder(newOrder)) {
            return false;
        }
        Todo todo = this.todoRepository.findById(id);
        if (todo != null && isDifferentOrder(todo, newOrder)) {
            if (todo.getOrder() > newOrder) {
                updateOrderTodos(newOrder, todo.getOrder(), true);
            } else {
                updateOrderTodos(todo.getOrder(), newOrder, false);
            }
            todo.setOrder(newOrder);
            this.todoRepository.save(todo);
            return true;
        } else if (todo != null && !isDifferentOrder(todo, newOrder)) {
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
        return this.todoRepository.findFirstByTitleIsNotNullOrderByOrderDesc().getOrder() >= order;
    }
}
