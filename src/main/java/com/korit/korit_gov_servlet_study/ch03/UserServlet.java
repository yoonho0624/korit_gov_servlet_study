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
import java.util.List;

@WebServlet("/ch03/users")
public class UserServlet extends HttpServlet {
    private UserRepository userRepository;
    private Gson gson;
    @Override
    public void init() throws ServletException {
        userRepository = UserRepository.getInstance();
        gson = new GsonBuilder().create();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 전체 조회
        // 근데 이제 json으로
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        User user = gson.fromJson(req.getReader(), User.class);
        List<User> foundUser = userRepository.userList(user);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");
        SuccesResponse<List> succesResponse = SuccesResponse.<List>builder()
                .status(200)
                .message("전체 출력")
                .body(foundUser)
                .build();
        String json = gson.toJson(succesResponse);
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        UserDto userDto = gson.fromJson(req.getReader(), UserDto.class);
        User foundUser = userRepository.findByUsername(userDto.getUsername());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json");
        if (foundUser != null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(400)
                    .message("이미 존재하는 username입니다.")
                    .build();
            resp.setStatus(400);
            String json = gson.toJson(errorResponse);
            resp.getWriter().write(json);
            return;
        }
        User user = userRepository.addUser(userDto.toEntity());

        SuccesResponse<User> succesResponse = SuccesResponse.<User>builder()
                .status(200)
                .message("사용자 등록이 안료되었습니다")
                .body(user)
                .build();
        String json = gson.toJson(succesResponse);
        resp.setStatus(200);
        resp.getWriter().write(json);
    }
}
