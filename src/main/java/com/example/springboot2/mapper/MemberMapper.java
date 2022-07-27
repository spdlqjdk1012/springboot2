package com.example.springboot2.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {
    List<Map<String, Object>> selectMember();

    Map<String, Object> selectMemberById(String name);
}
