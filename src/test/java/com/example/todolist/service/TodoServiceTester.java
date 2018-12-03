package com.example.todolist.service;

import com.example.todolist.repository.TodoRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTester {

  @InjectMocks
  private TodoService todoService;

  @Mock
  TodoRepository todoRepository;
}
