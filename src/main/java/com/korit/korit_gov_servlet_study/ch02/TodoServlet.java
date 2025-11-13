package com.korit.korit_gov_servlet_study.ch02;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/ch02/todos")
public class TodoServlet extends HttpServlet {
    private List<Todo> todos;

    @Override
    public void init() throws ServletException {
        todos = new ArrayList<>();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        List<Todo> foundTodos = todos.stream().filter(todo -> todo.getTitle().equals(req.getParameter("title"))).toList();
        Todo foundTodo = foundTodos.isEmpty() ? null : foundTodos.get(0);
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        if (foundTodo == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("해당 todo가 없습니다");
            return;
        }
        resp.getWriter().println(foundTodo);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String username = req.getParameter("username");

        Todo todo = Todo.builder()
                .title(title)
                .content(content)
                .username(username)
                .build();
        Map<String, String> error = validTodo(todo);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        if (!error.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println(error);
            return;
        }
        todos.add(todo);
        System.out.println(todos);
        resp.setStatus(HttpServletResponse.SC_OK); // 200
        resp.getWriter().println("Todo 등록 완료");
    }
    private Map<String, String> validTodo(Todo todo) {
        Map<String, String> error = new HashMap<>();
        Arrays.stream(todo.getClass().getDeclaredFields()).forEach(f -> {
            f.setAccessible(true);
            String field = f.getName();
            try {
                Object fieldValue = f.get(todo);
                if (fieldValue == null) throw new RuntimeException();
                if (fieldValue.toString().isBlank()) throw new RuntimeException();
            } catch (IllegalAccessException e) {
                System.out.println("잘못된 입력");
            } catch (RuntimeException e) {
                error.put(field, "빈값 일 수 없습니다.");
            }
        });
        return error;
    }
    /*
    * List에 투두들 저장
    * 저장요청시 쿼리파라미터에서 값을 가져와 리스트에 저장
    * 저장 전에 3가지 필드가 다 채워져 있는지 확인
    * 빈값있으면 map에 필드명과 빈값 일 수 없습니다. 넣고 응답
    * 조회 요청시 쿼리파라미터가 없으면 전체 조회
    * 있으면 title로 단건 조회
    * 해당 title 투두가 없으면 해당 투두가 없습니다. 응답
    * */
}
