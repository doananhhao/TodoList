package com.example.todolist.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TodoDto {
  int id;
  String title;
  boolean completed;
  int order;
}
