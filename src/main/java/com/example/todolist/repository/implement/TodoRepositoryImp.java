package com.example.todolist.repository.implement;

import com.example.todolist.models.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
public class TodoRepositoryImp implements TodoRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Todo> getTodos(Boolean status) {
        try {
            String sql = "SELECT t FROM Todo t";

            if (status != null) {
                sql += " WHERE t.completed = :status ORDER BY t.id ASC";
                Query query = this.entityManager.createQuery(sql, Todo.class);
                query.setParameter("status", status);
                return query.getResultList();
            }

            sql += " ORDER BY t.id ASC";
            Query query = this.entityManager.createQuery(sql, Todo.class);
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Todo addTodo(Todo todo) {
        try {
            this.entityManager.persist(todo);
            return todo;
        } catch (Exception e) {
            return null;
        }
    }

    public Todo getTodo(int id) {
        return this.entityManager.find(Todo.class, id);
    }

    @Override
    public Todo updateTodo(Todo todo) {
        try {
            Todo currentTodo = this.getTodo(todo.getId());
            currentTodo.setTitle(todo.getTitle());
            currentTodo.setCompleted(todo.isCompleted());
            this.entityManager.flush();
            return currentTodo;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean deleteTodo(int id) {
        try {
            Todo todo = this.getTodo(id);
            this.entityManager.remove(todo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
