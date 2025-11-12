package com.korit.korit_gov_servlet_study.ch01;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
/*
* 서블릿
* 클라이언트의 요청을 처리하고 그 결과를 반환하는 servlet 클래스의 구현 규칙을 지킨 자바 웹 프로그래밍
* */
public class FirstServlet extends HttpServlet {
    /*
    * 서블릿 초기화 메소드, 컨테이너(홈캣 서버)가 딱 1번 호출
    * 생성자 -> init() -> (요청마다)service() -> (요청메소드에 따라서)doGet, doPost... 등등 호출
    * -> (서버가 꺼지면)destroy()
    * */
    public FirstServlet() {
        System.out.println("FirstServlet 생성자 호출");
    }
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init 메소드 호출 초기화");
        config.getServletContext().setAttribute("age", 27);
    }
    @Override
    public void destroy() {
        System.out.println("destroy 메소드 호출 소멸");
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.service(req, resp);
        System.out.println("service 메소드 호출 요청 들어옴");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
