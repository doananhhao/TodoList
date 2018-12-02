//package com.example.todolist.service;
//
//import com.example.todolist.models.Todo;
//import com.example.todolist.repository.TodoRepositoryCustom;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//
//@RunWith(MockitoJUnitRunner.class)
//public class TodoServiceTester {
//
//  @InjectMocks
//  private TodoService todoService;
//
//  @Mock
//  TodoRepositoryCustom todoRepository;
//
//  @Test
//  public void testAddTodo() {
//    String titleForNull = "";
//    String titleForNotNull = "test";
//
//    addForNull(titleForNull);
//    addForNotNull(titleForNotNull);
//  }
//
//  private void addForNotNull(String title) {
//    Todo todo = new Todo();
//    todo.setCompleted(false);
//    todo.setTitle(title);
//
//    Todo returnedTodo = todo;
//    returnedTodo.setId(1);
//    Mockito.when(this.todoRepository.addTodo(todo)).thenReturn(returnedTodo);
//    returnedTodo = this.todoService.addTodo(todo);
//    Mockito.verify(this.todoRepository).addTodo(todo);
//    Assert.assertNotEquals(returnedTodo, null);
//    checkTodo(returnedTodo);
//  }
//
//  private void addForNull(String title) {
//    Todo todo = new Todo();
//    todo.setCompleted(false);
//    todo.setTitle(title);
//    Todo returnedTodo = this.todoService.addTodo(todo);
//    Assert.assertNull(returnedTodo);
//  }
//
//  private void checkTodo(Todo todo) {
//    Assert.assertTrue(todo.getId() != 0);
//    Assert.assertTrue(!todo.getTitle().equalsIgnoreCase(""));
//    Assert.assertTrue(todo.isCompleted() || !todo.isCompleted());
//  }
//
//}
