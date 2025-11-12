package com.korit.korit_gov_servlet_study.ch02;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/*
* HTTP 프로토콜 메소드
* 1. GET
*   - 용도 : 리소스 조회
*   - 특징 :
*       서버로부터 데이터를 요청만 하고 수정은 하지 않음
*       요청 데이터(파라미터)가 URL에 포함됨
*       멱등성 O
* 2. POST
*   - 용도 : 새로운 리소스 생성
*   - 특징 :
*       서버에 데이터를 전송하여 새로운 리소스 생성
*       요청 데이터가 HTTP Body에 포함됨
*       멱등성 X
* 3. PUT
*   - 용도 : 리소스 전체 수정 및 생성
*   - 특징 :
*       리소스가 있으면 전체를 교체, 없으면 생성
*       전체 데이터를 전송해야함
*       멱등성 O
* 4. PATCH
*   - 용도 : 리소스 부분 수정
*   - 특징 :
*       리소스의 일부만 수정
*       PUT보다 효율적(변경할 필드만 전송)
*       멱등성 X
* 5. DELETE
*   - 용도 : 리소스 삭제
*   - 특징 :
*       지정된 리소스를 삭제
*       멱등성 O
* 6. HEAD
*   - 용도 : 리소스 존재여부 또는 메타데이터 확인
* 7. OPTIONS
*   - 용도 : HTTP 메소드의 존재여부 또는 CORS 프리플라이트 요청에 사용
* 8. CONNECT
*   - 용도 : 프록시 서버를 통한 터널링에 사용, SSL 연결에 활용
* 9. TRACE
*   - 용도 : 디버깅
* */
@WebServlet("/ch02/method")
public class HttpMethodServlet extends HttpServlet {
    Map<String, String > datas = new HashMap<>(Map.of(
            "name", "박윤호",
            "age", "25",
            "address", "김해시"
    ));
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("GET 요청 들어옴");
        System.out.println("요청 메소드 : " + req.getMethod());
        System.out.println("요청 쿼리 파라미터(datasKey) : " + req.getParameter("datasKey"));
        String datasKey = req.getParameter("datasKey");
        System.out.println(datas.get(datasKey));

        // 응답
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter out = resp.getWriter(); // 문자 출력용
        out.println(datas.get(datasKey));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST 요청 들어옴");

        System.out.println("요청 메소드 : " + req.getMethod());
        System.out.println("요청 쿼리 파라미터(keyName) : " + req.getParameter("keyName"));
        System.out.println("요청 쿼리 파라미터(value) : " + req.getParameter("value"));
        datas.put(req.getParameter("keyName"), req.getParameter("value"));

        System.out.println(datas.toString());

        // 응답
        resp.setStatus(201);
        resp.setContentType("text/plain");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.getWriter().println("데이터 추가 성공!@!@");
    }
}








































