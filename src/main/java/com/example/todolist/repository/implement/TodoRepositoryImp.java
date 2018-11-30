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
        sql += " WHERE t.completed = :status ORDER BY t.order ASC";
        Query query = this.entityManager.createQuery(sql, Todo.class);
        query.setParameter("status", status);
        return query.getResultList();
      }

      sql += " ORDER BY t.order ASC";
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

  @Override
  public int countTodos(Boolean status) {
    try {
      String sql = "SELECT COUNT(t) FROM Todo t";
      Query query;
      if (status != null) {
        sql += " WHERE t.completed = :status";
        query = this.entityManager.createQuery(sql);
        query.setParameter("status", status);
      } else {
        query = this.entityManager.createQuery(sql);
      }
      return Math.toIntExact((Long) query.getSingleResult());
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public Todo findByTitle(String title) {
    try {
      String sql = "SELECT t FROM Todo t WHERE t.title LIKE :title";
      Query query = this.entityManager.createQuery(sql, Todo.class);
      query.setMaxResults(1);
      query.setParameter("title", title);
      return (Todo) query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public Todo findById(int id) {
    try {
      String sql = "SELECT t FROM Todo t WHERE t.id = :id";
      Query query = this.entityManager.createQuery(sql, Todo.class);
      query.setMaxResults(1);
      query.setParameter("id", id);
      return (Todo) query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public boolean changeOrderTodos(int from, int to, boolean isIncreaseOrder) {
    try {
      String sql;
      if (isIncreaseOrder) {
        sql = "UPDATE todo SET ordered = ordered + 1 WHERE ordered >= :from AND ordered <= :to";
      } else {
        sql = "UPDATE todo SET ordered = ordered - 1 WHERE ordered >= :from AND ordered <= :to";
      }
      Query query = this.entityManager.createNativeQuery(sql);
      query.setParameter("from", from);
      query.setParameter("to", to);
      int effectedRow = query.executeUpdate();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public int getMaxOrder() {
    try {
      String sql = "SELECT MAX(t.order) FROM Todo t";
      Query query = this.entityManager.createQuery(sql);
      return (Integer) query.getSingleResult();
    } catch (Exception e) {
      return 0;
    }
  }
}
