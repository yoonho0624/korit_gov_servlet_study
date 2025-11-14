package com.korit.korit_gov_servlet_study.ch04;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
@WebFilter("/ch04/*")
public class FirstFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("필터 초기화");
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("필터 (전처리) : 요청 들어오는 중");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("필터 (후처리) : 응답 나가는 중");
    }
    @Override
    public void destroy() {
        System.out.println("필터 소멸");
    }
}
