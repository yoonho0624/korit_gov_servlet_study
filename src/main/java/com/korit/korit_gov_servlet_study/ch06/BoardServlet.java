package com.korit.korit_gov_servlet_study.ch06;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ch06/boards")
public class BoardServlet extends HttpServlet {
    private Gson gson;
    private BoardRepository boardRepository;

    @Override
    public void init() throws ServletException {
        gson = new GsonBuilder().create();
        boardRepository = BoardRepository.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Board board = gson.fromJson(req.getReader(), Board.class);
        SuccesResponse succesResponse = SuccesResponse.builder()
                .message("게시물 작성 완료")
                .build();
        boardRepository.boardList(board);
        String json = gson.toJson(succesResponse);
        resp.setStatus(200);
        resp.getWriter().write(json);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = gson.toJson(boardRepository.allBoard());
        resp.setStatus(200);
        resp.getWriter().write(json);
    }
}
