package com.example.todolist.models;

public class Statistic {
  String name;
  int count;
  int percent;

  public Statistic(String name, int count, int percent) {
    this.name = name;
    this.count = count;
    this.percent = percent;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getPercent() {
    return percent;
  }

  public void setPercent(int percent) {
    this.percent = percent;
  }

  public Statistic() {
  }
}
