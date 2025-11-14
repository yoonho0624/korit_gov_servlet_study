package com.korit.korit_gov_servlet_study.ch05;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ch05/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("로그인페이지 요청 들어옴");
        resp.setContentType("text/plain");
        resp.getWriter().write("로그인 페이지입니다.");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        boolean isValidUser = "test".equals(username) && "1234".equals(password);

        if (!isValidUser) {
            resp.getWriter().write("로그인 실패");
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("principal", username);

        resp.sendRedirect(req.getContextPath() + "/ch05/mypage/home");
    }
}
