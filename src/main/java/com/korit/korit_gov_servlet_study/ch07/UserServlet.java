package com.korit.korit_gov_servlet_study.ch07;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ch07/users")
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
        resp.setContentType("application/json");
        ResponseDto<Object> responseDto = ResponseDto.builder()
                .status(200)
                .body(userRepository.allUser())
                .build();
        resp.setStatus(200);
        String json = gson.toJson(responseDto);
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignupReqDto signupReqDto = gson.fromJson(req.getReader(), SignupReqDto.class);
        User foundUser = userRepository.findByUsername(signupReqDto.getUsername());
        resp.setContentType("application/json");
        if (foundUser != null) {
            ResponseDto<Object> responseDto = ResponseDto.builder()
                    .status(200)
                    .message("이미 존재하는 username입니다.")
                    .body(null)
                    .build();
            resp.setStatus(200);
            String json = gson.toJson(responseDto);
            resp.getWriter().write(json);
            return;
        }
        User user = userRepository.addUser(signupReqDto.toEntity());
        ResponseDto<Object> responseDto = ResponseDto.builder()
                .status(200)
                .message("사용자 등록이 안료되었습니다")
                .body(user)
                .build();
        String json = gson.toJson(responseDto);
        resp.setStatus(200);
        resp.getWriter().write(json);
    }
}
