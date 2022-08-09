package com.example.springboot2.controller;

import com.example.springboot2.dto.MemberDTO;
import com.example.springboot2.dto.ResponseDTO;
import com.example.springboot2.exception.BadRequestException;
import com.example.springboot2.jwt.CustomUserDetails;
import com.example.springboot2.jwt.JwtTokenProvider;
import com.example.springboot2.jwt.TokenResponse;
import com.example.springboot2.redis.RedisService;
import com.example.springboot2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MainController {
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private RedisService redisService;

    @Autowired
    MemberService memberService;
    @GetMapping(value = "/main")
    public ModelAndView main(){
        ModelAndView memberListView = new ModelAndView("user/main");
        return memberListView;
    }

    @GetMapping(value = "/login/form")
    public ModelAndView loginForm(){
        ModelAndView memberListView = new ModelAndView("user/login");
        return memberListView;
    }
/*
    @PostMapping(value="/login/loginProcess")
    public ResponseEntity loginProcess(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();

        String username = ""+request.getParameter("username");

        if( StringUtils.isEmpty( username )){
            throw new BadRequestException("아이디 입력해주세요");
        }


        String password = ""+request.getParameter("password");
        Map<String, Object> mem = memberService.selectMemberByIdAndPw(username, password);
        if(mem==null){
            mv.setViewName("redirect:/login/form");
            return mv;
        }
        String midx = ""+mem.get("midx");
        TokenResponse jwt = jwtTokenProvider.createToken(username, midx);

        Cookie cookie = new Cookie("jwt", jwt.getAccessToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        mv.setViewName("redirect:/main");
        return mv;
    }*/
    @PostMapping(value="/login/loginProcess")
    @ResponseBody
    public ResponseEntity<ResponseDTO> loginProcess(@RequestBody MemberDTO.LoginRequest member, HttpServletRequest request, HttpServletResponse response) {
        /*String username = ""+request.getParameter("username");
        if( StringUtils.isEmpty( username )){
            throw new BadRequestException("아이디 입력해주세요");
        }
        String password = ""+request.getParameter("password");
        if( StringUtils.isEmpty( password )){
            throw new BadRequestException("비밀번호를 입력해주세요");
        }*/
        if("".equals(member.getUsername())){
            throw new BadRequestException("아이디 입력해주세요");
        }
        if("".equals(member.getPassword())){
            throw new BadRequestException("비밀번호 입력해주세요");
        }
        
        Map<String, Object> mem = memberService.selectMemberByIdAndPw(member.getUsername(), member.getPassword());
        if(mem==null){
            throw new BadRequestException("로그인 정보가 일치 하지 않습니다.");
            //return new ResponseEntity<>(ResponseDTO.setFail(500, "로그인 정보가 일치 하지 않습니다.") ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String midx = ""+mem.get("midx");
        TokenResponse jwt = jwtTokenProvider.createToken(member.getUsername(), midx, request);
        Cookie cookie = new Cookie("jwt", jwt.getAccessToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return new ResponseEntity<>(ResponseDTO.setSuccess("로그인 성공"), HttpStatus.OK);
    }
    @GetMapping(value="/logoutProcess")
    public ModelAndView loginlogout(HttpServletRequest request, HttpServletResponse response) {
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
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/login/form");
        return mv;
    }

    @GetMapping(value="/myInfo")
    public ModelAndView myInfo(@AuthenticationPrincipal CustomUserDetails userDetail) {
        ModelAndView memberListView = new ModelAndView("user/myInfo");
        memberListView.addObject("nickname", userDetail.getUsername());
        memberListView.addObject("password", userDetail.getPassword());
        return memberListView;
    }

    @GetMapping(value="/login/deny")
    public ModelAndView deny(@AuthenticationPrincipal CustomUserDetails userDetail, Model model) {
        ModelAndView memberListView = new ModelAndView("user/deny");
        return memberListView;
    }

    @GetMapping("/exception")
    public String exception1(){
        throw new NullPointerException();
    }

    @GetMapping("/exception2")
    public String exception2(){
        throw new ClassCastException();
    }

//    @ExceptionHandler({NullPointerException.class, ClassCastException.class})
//    public String handle(Exception ex){
//        return "Exception Handle!!!";
//    }
}
