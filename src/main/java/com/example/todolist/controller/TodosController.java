package com.example.todolist.controller;

import com.example.todolist.models.Todo;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "api/todo")
public class TodosController {

  public static final String APPLICATION_JSON = "application/json";
  public static final String SUCCESS = "success";
  public static final String RESULT = "result";

  @Autowired
  private TodoService todoService;

  @ResponseBody
  @GetMapping(produces = APPLICATION_JSON)
  public ResponseEntity<List<Todo>> getTodos(@RequestParam(name = "status") String status) {
    return ResponseEntity.ok(this.todoService.getTodos(status));
  }

  @ResponseBody
  @GetMapping(value = "/count-remaining-todos", produces = APPLICATION_JSON)
  public ResponseEntity<Integer> getRemainingCount() {
    return ResponseEntity.ok(this.todoService.countRemaining());
  }

  @ResponseBody
  @PostMapping(value = "/add", produces = APPLICATION_JSON)
  public ResponseEntity addTodo(@RequestBody Map<String, String> requestParams) {
    Map<String, Object> responseData = new HashMap<>();
    Todo todo = new Todo();
    todo.setTitle(requestParams.get("title"));
    todo.setCompleted(false);

    todo = this.todoService.addTodo(todo);
    if (todo == null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } else {
      return ResponseEntity.ok(todo);
    }
  }

  @ResponseBody
  @PutMapping(value = "/{id}/edit", produces = APPLICATION_JSON)
  public ResponseEntity updateTodo(@PathVariable int id,
      @RequestBody Todo todo) {
    todo.setId(id);
    Todo newTodo = this.todoService.updateTodo(todo);
    return newTodo != null ? ResponseEntity.ok(newTodo) : ResponseEntity.badRequest().build();
  }

  @ResponseBody
  @DeleteMapping(value = "/{id}/delete", produces = APPLICATION_JSON)
  public ResponseEntity deleteTodo(@PathVariable int id) {
    boolean isDeleted = this.todoService.deleteTodo(id);
    if (!isDeleted) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok().build();
  }

  @ResponseBody
  @PutMapping(value = "/change-order", produces = APPLICATION_JSON)
  public ResponseEntity setOrderTodos(@RequestBody Map<String, Integer> requestParams) {
    int todoId = requestParams.get("todoId");
    int newOrder = requestParams.get("newOrder");
    boolean success = this.todoService.changeOrderTodo(todoId, newOrder);
    if (!success) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }

}
