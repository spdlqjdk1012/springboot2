package com.example.springboot2.controller;

import com.example.springboot2.jwt.JwtTokenProvider;
import com.example.springboot2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    MemberService memberService;

    @GetMapping(value = "/memberList")
    public ModelAndView memberList(){
        ModelAndView mv = new ModelAndView("admin/memberList");
        List<Map<String, Object>> memberList = memberService.selectMember();
        mv.addObject("memberList", memberList);
        return mv;
    }
}
