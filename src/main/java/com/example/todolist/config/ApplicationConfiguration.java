package com.example.todolist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.example.todolist.repository")
public class ApplicationConfiguration {
}
