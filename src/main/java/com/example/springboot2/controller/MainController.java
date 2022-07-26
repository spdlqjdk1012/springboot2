package com.example.springboot2.controller;

import com.example.springboot2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    MemberService memberService;
    @RequestMapping(value = "/main")
    public String main(){
        return "user/main";
    }

    @RequestMapping(value = "/login/form")
    public String loginForm(){
        return "user/login";
    }
}
