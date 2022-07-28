package com.example.springboot2.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean{
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        System.out.println("token:"+token);
        // 유효한 토큰인지 확인합니다.
        if (token != null) {
            // access token 유효하지 않을 경우
            if(!jwtTokenProvider.validateToken(token)){
                String refresh = jwtTokenProvider.getRefreshToken(token);
                //refresh 토큰도 유효하지 않을 경우
                if(!jwtTokenProvider.validateToken(refresh)){
                    SecurityContextHolder.getContext().setAuthentication(null);
                    chain.doFilter(request, response);
                    return;
                }

                //access 토큰 갱신
                token = jwtTokenProvider.setAccessToken(token, refresh,(HttpServletRequest) request,(HttpServletResponse) response);

            }
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext 에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        chain.doFilter(request, response);
    }
}
