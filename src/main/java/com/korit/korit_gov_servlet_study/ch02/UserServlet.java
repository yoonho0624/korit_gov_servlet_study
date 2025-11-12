package com.korit.korit_gov_servlet_study.ch02;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/ch02/users")
public class UserServlet extends HttpServlet {
    private List<User> users;

    @Override
    public void init() throws ServletException {
        users = new ArrayList<>();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // username으로 user찾기
        // 찾으면 user객체 응답(200), 없으면 username은 존재하지 않습니다.(404)
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        List<User> foundUsers = users.stream().filter(user -> user.getUsername().equals(req.getParameter("username"))).toList();
        User foundUser = foundUsers.isEmpty() ? null : foundUsers.get(0);
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        if (foundUser == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("해당 username은 존재하지 않습니다");
            return;
        }
        resp.getWriter().println(foundUser);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String email = req.getParameter("email");

        User user = User.builder()
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .build();

        Map<String, String> error = vaildUser(user);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        // 에러 응답
        if (!error.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            resp.getWriter().println(error);
            return;
        }

        users.add(user);
        System.out.println(users);
        resp.setStatus(HttpServletResponse.SC_OK); // 200
        resp.getWriter().println("사용자 등록 완료");
    }
    private Map<String, String> vaildUser(User user) {
        Map<String, String> error = new HashMap<>();
        // user 객체의 선언된 모든 필드(접근제어자 무관)를 스트림으로 순회
        Arrays.stream(user.getClass().getDeclaredFields()).forEach(f-> {
            f.setAccessible(true); // private 필드에도 접근할 수 있게 강제로 접근 허용
            String fieldName = f.getName();
            System.out.println(fieldName);
            try {
                // 리플렉션으로 user 인스턴스의 해당 필드값 꺼낵;
                Object fieldValue = f.get(user);
                System.out.println(fieldValue);
                // 만약 해당 필드값이 null이면 검증 실패로 간주
                if (fieldValue == null) throw new RuntimeException();
                // 필드값이 문자열일 때 공백/빈문자열이면 실패로 간주
                if (fieldValue.toString().isBlank()) throw new RuntimeException();
            } catch (IllegalAccessException e) {
                // 필드 접근 권한 문제(드물게 발생)
                System.out.println("필드에 접글할 수 없습니다.");
            }
            catch (RuntimeException e) {
                // 위에서 던진 예외를 여기서 받아서 해당 필드에 대한 에러메시지 추가
                error.put(fieldName, "빈 값일 수 없습니다.");
            }
        });
        return error;
    }
}
