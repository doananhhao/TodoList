package com.example.todolist.controller;

import com.example.todolist.dto.ChangeOrderDto;
import com.example.todolist.dto.TodoDto;
import com.example.todolist.models.Todo;
import com.example.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/todo")
public class TodosController {

  public static final String APPLICATION_JSON = "application/json";

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
  @GetMapping(value = "/count-completed-todos", produces = APPLICATION_JSON)
  public ResponseEntity<Integer> getCompletedCount() {
    return ResponseEntity.ok(this.todoService.countCompleted());
  }

  @ResponseBody
  @PostMapping(value = "/add", produces = APPLICATION_JSON)
  public ResponseEntity addTodo(@RequestBody TodoDto todoDto) {
    Todo todo = this.todoService.addTodo(todoDto);
    if (todo == null) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    } else {
      return ResponseEntity.ok(todo);
    }
  }

  @ResponseBody
  @PutMapping(value = "/edit", produces = APPLICATION_JSON)
  public ResponseEntity updateTodo(@RequestBody TodoDto todoDto) {
    Todo newTodo = this.todoService.updateTodo(todoDto);
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
  public ResponseEntity setOrderTodos(@RequestBody ChangeOrderDto changeOrderDto) {
    boolean success = this.todoService.changeOrderTodo(changeOrderDto);
    if (!success) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok().build();
  }

}
