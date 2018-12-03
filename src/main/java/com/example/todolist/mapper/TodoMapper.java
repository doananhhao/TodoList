package com.example.todolist.mapper;

import com.example.todolist.dto.TodoDto;
import com.example.todolist.models.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.nio.CharBuffer;

@Mapper(componentModel = "spring",
  imports = {CharBuffer.class},
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TodoMapper {
  Todo todoDtoToTodo(TodoDto todoDto);
}
