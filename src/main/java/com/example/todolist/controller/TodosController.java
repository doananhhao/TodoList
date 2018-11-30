package com.example.todolist.controller;

import com.example.todolist.models.Todo;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
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
  @RequestMapping(method = RequestMethod.GET, produces = APPLICATION_JSON)
  public Map getTodos(@RequestParam(name = "status", required = false) String status) {
    Map<String, List> responseData = new HashMap<>();
    responseData.put("data", this.todoService.getTodos());
    return responseData;
  }

  @ResponseBody
  @RequestMapping(value = "/count-remaining-todos", method = RequestMethod.GET, produces = APPLICATION_JSON)
  public int getRemainingCount() {
    return this.todoService.countRemaining();
  }

  @ResponseBody
  @RequestMapping(value = "/add", method = RequestMethod.POST, produces = APPLICATION_JSON)
  public Map addTodo(@RequestBody Map<String, String> requestParams) {
    Map<String, Object> responseData = new HashMap<>();
    Todo todo = new Todo();
    todo.setTitle(requestParams.get("title"));
    todo.setCompleted(false);

    todo = this.todoService.addTodo(todo);
    if (todo == null) {
      responseData.put(SUCCESS, false);
      responseData.put(RESULT, "This todo '" + requestParams.get("title") + "' is already exists");
    } else {
      responseData.put(SUCCESS, true);
      responseData.put(RESULT, todo);
    }
    return responseData;
  }

  @ResponseBody
  @RequestMapping(value = "/{id}/edit", method = RequestMethod.PUT, produces = APPLICATION_JSON)
  public Todo updateTodo(@PathVariable int id,
      @RequestBody Todo todo) {
    todo.setId(id);
    return this.todoService.updateTodo(todo);
  }

  @ResponseBody
  @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE, produces = APPLICATION_JSON)
  public boolean deleteTodo(@PathVariable int id) {
    return this.todoService.deleteTodo(id);
  }

  @ResponseBody
  @RequestMapping(value = "/change-order", method = RequestMethod.PUT, produces = APPLICATION_JSON)
  public Map setOrderTodos(@RequestBody Map<String, Integer> requestParams) {
    int todoId = requestParams.get("todoId");
    int newOrder = requestParams.get("newOrder");
    boolean success = this.todoService.changeTodoOrder(todoId, newOrder);
    Map<String, Object> responseData = new HashMap<>();
    responseData.put(SUCCESS, success);
    if (!success) {
      responseData.put(RESULT, String.format("Todo may not be exist or has invalid index (index: %d)", newOrder));
    }
    return responseData;
  }

}
