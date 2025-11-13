package com.korit.korit_gov_servlet_study.ch03;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/ch03/todos")
public class TodoServlet extends HttpServlet {
    private TodoRepository todoRepository;
    private Gson gson;
    @Override
    public void init() throws ServletException {
        todoRepository = TodoRepository.getInstance();
        gson = new GsonBuilder().create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        SuccesResponse<Object> succesResponse = SuccesResponse.builder()
                .status(200)
                .message("조회")
                .body(todoRepository.allTodo())
                .build();
        String json = gson.toJson(succesResponse);
        resp.setStatus(200);
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Todo todo = gson.fromJson(req.getReader(), Todo.class);
        resp.setContentType("application/json");
        Todo foundTodo = todoRepository.findByUsername(todo.getUsername());
        if (foundTodo != null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(400)
                    .message("중복되는 username이 있습니다.")
                    .build();
            String json = gson.toJson(errorResponse);
            resp.setStatus(400);
            resp.getWriter().write(json);
            return;
        }
        SuccesResponse<Object> succesResponse = SuccesResponse.builder()
                .status(200)
                .message("사용자 등록 완료")
                .body(todoRepository.addTodo(todo))
                .build();
        String json = gson.toJson(succesResponse);
        resp.setStatus(200);
        resp.getWriter().write(json);
    }
}
