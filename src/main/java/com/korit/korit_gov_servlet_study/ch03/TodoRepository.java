package com.korit.korit_gov_servlet_study.ch03;

import java.util.ArrayList;
import java.util.List;

public class TodoRepository {
    private static TodoRepository instance;
    private List<Todo> todos;
    private TodoRepository() {
        todos = new ArrayList<>();
    }
    public static TodoRepository getInstance() {
        if (instance == null) instance = new TodoRepository();
        return instance;
    }
    public Todo addTodo(Todo todo) {
        todos.add(todo);
        return todo;
    }
    public List<Todo> allTodo() {
        return todos;
    }
    public Todo findByUsername(String username) {
        return todos.stream()
                .filter(todo -> todo.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
