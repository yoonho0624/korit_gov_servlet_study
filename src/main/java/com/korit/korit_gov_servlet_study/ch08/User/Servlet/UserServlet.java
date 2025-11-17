package com.korit.korit_gov_servlet_study.ch08.User.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.korit.korit_gov_servlet_study.ch08.User.Dto.ApiRespDto;
import com.korit.korit_gov_servlet_study.ch08.User.Dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch08.User.Entity.User;
import com.korit.korit_gov_servlet_study.ch08.User.Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ch08/user")
public class UserServlet extends HttpServlet {
    private UserService userService;
    //private ObjectMapper objectMapper;
    private Gson gson;
    @Override
    public void init() throws ServletException {
        userService = UserService.getInstance();
        //objectMapper = new ObjectMapper();
        gson = new GsonBuilder().create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // body json을 가져오면 dto로 변환해서 username중복검사 한 후 서비스에 전달 후 db에 추가
        // 1. body json가져오기 => dto변환 (json에서 변환하려면 Gson 필요)
        SignupReqDto signupReqDto = gson.fromJson(req.getReader(), SignupReqDto.class);
        ApiRespDto<?> apiRespDto = null;
        // 2. dto의 username을 중복 검사 => 서비스에서 중복인지 아닌지 판단 메소드
        if (userService.isDuplicatedUsername(signupReqDto.getUsername())) {
            apiRespDto = ApiRespDto.<String>builder()
                    .status("failed")
                    .message(signupReqDto.getUsername() + "은 이미 사용중인 username입니다.")
                    .body(signupReqDto.getUsername())
                    .build();
        }
        else {
            // 3. 추가하기
            // 추가하려면 userService 추가 메소드
            apiRespDto = ApiRespDto.builder()
                    .status("success")
                    .message("정상적으로 회원가입이 되었습니다.")
                    .body(userService.addUser(signupReqDto))
                    .build();
        }
        // 4. 응답 보내기
        String json = gson.toJson(apiRespDto);
        resp.getWriter().write(json);
    }
}
