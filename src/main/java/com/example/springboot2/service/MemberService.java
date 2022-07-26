package com.example.springboot2.service;

import com.example.springboot2.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberService {
    @Autowired
    public MemberMapper mapper;

    public List<Map<String, Object>> selectMember(){
        return mapper.selectMember();
    }
    public Map<String, Object> selectMemberByIdAndPw(String id, String pw){
        return mapper.selectMemberByIdAndPw(id, pw);
    }
}
