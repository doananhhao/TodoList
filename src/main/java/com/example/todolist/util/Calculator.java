package com.example.todolist.util;

public class Calculator {
  public static int calculatePercentage(int count, int total) {
    double percentage = ((count * 1.0d) / total) * 100.0d;
    return (int) Math.floor(percentage);
  }
}
