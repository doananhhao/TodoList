package com.example.todolist.models;

import javax.persistence.*;

@Entity
@Table(name = "TODO")
public class Todo {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "title", length = 500, nullable = false)
    private String title;
    @Column(name = "completed", nullable = false)
    private boolean completed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
