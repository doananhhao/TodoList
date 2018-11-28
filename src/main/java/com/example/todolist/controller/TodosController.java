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

    @Autowired
    private TodoService todoService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Map getTodos(@RequestParam(name = "status", required = false) String status) {
        Map<String, List> responseData = new HashMap<>();
        responseData.put("data", this.todoService.getTodos());
        System.out.println(status);
        return responseData;
    }

    @ResponseBody
    @RequestMapping(value = "/{title}/add", method = RequestMethod.GET, produces = "application/json")
    public Todo addTodo(@PathVariable String title) {
        return this.todoService.addTodo(title);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.PUT, produces = "application/json")
    public Todo updateTodo(@PathVariable int id,
                           @RequestBody Todo todo) {
        todo.setId(id);
        return this.todoService.updateTodo(todo);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE, produces = "application/json")
    public boolean deleteTodo(@PathVariable int id) {
        return this.todoService.deleteTodo(id);
    }

}
