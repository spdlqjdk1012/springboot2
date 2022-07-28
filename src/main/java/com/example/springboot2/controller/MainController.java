package com.example.springboot2.controller;

import com.example.springboot2.jwt.CustomUserDetails;
import com.example.springboot2.jwt.JwtTokenProvider;
import com.example.springboot2.jwt.TokenResponse;
import com.example.springboot2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class MainController {
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    MemberService memberService;
    @GetMapping(value = "/main")
    public String main(){
        return "user/main";
    }

    @GetMapping(value = "/login/form")
    public String loginForm(){
        return "user/login";
    }

    @RequestMapping(value="/login/loginProcess")
    public String loginProcess(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("test login");
        String username = ""+request.getParameter("username");
        String password = ""+request.getParameter("password");
        Map<String, Object> mem = memberService.selectMemberByIdAndPw(username, password);
        if(mem==null){
            return "redirect:/login/form";
        }
        String midx = ""+mem.get("midx");
        TokenResponse jwt = jwtTokenProvider.createToken(username, midx);

        Cookie cookie = new Cookie("jwt", jwt.getAccessToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return "redirect:/main";
    }
    @RequestMapping(value="/logoutProcess")
    public String loginlogout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie c : cookies) {
                String name = c.getName(); // 쿠키 이름 가져오기
                String value = c.getValue(); // 쿠키 값 가져오기
                if (name.equals("jwt")) {
                    c.setValue("");
                    c.setPath("/");
                    c.setMaxAge(0);
                    response.addCookie(c);
                    break;
                }
            }
        }
        return "redirect:/login/form";
    }

    @RequestMapping(value="/myInfo")
    public String myInfo(@AuthenticationPrincipal CustomUserDetails userDetail, Model model) {
        model.addAttribute("nickname", userDetail.getUsername());
        model.addAttribute("password", userDetail.getPassword());
        return "user/myInfo";
    }

    @RequestMapping(value="/login/deny")
    public String deny(@AuthenticationPrincipal CustomUserDetails userDetail, Model model) {
        model.addAttribute("nickname", userDetail.getUsername());
        model.addAttribute("password", userDetail.getPassword());
        return "user/deny";
    }
}
