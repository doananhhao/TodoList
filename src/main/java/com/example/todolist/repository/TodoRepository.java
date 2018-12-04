package com.example.todolist.repository;

import com.example.todolist.models.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Integer> {
    Todo findFirstByTitleIsNotNullOrderByOrderDesc();
    Todo findById(int id);
    Todo findByTitle(String title);
    Todo findByTitleAndIdNot(String title, int excludeId);
    int countAllByCompleted(Boolean completed);
    int countAllByTitleIsNotNull();
    List<Todo> findAllByOrderByOrderAsc();
    List<Todo> findAllByCompletedOrderByOrderAsc(Boolean completed);
    List<Todo> findByOrderBetween(int from, int to);
    void deleteById(int id);
}
