package com.example.springboot2.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberMapper {
    List<Map<String, Object>> selectMember();

    Map<String, Object> selectMemberById(String name);

    Map<String, Object> selectMemberByIdAndPw(String id, String pw);

    void insertToken(String pk, String refreshToken, String accessToken, String midx, String username);

    Map<String, Object> selectTokenByMemidx(String midx);

    Map<String, Object> selectTokenByAToken(String token);

    void deleteToken(String midx);

    Map<String, Object> selectAuth(String mname, String access, String refresh);

    void updateToken(String midx, String access, String refresh);
}
