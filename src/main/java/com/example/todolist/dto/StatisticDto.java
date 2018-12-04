package com.example.todolist.dto;

import com.example.todolist.models.Statistic;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatisticDto {
  Statistic remaining;
  Statistic completed;
}
