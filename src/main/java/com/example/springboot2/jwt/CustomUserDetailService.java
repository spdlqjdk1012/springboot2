package com.example.springboot2.jwt;

import com.example.springboot2.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    public MemberMapper mapper;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String role = "ROLE_USER";
        //List<Map<String, Object>> memberList = mapper.selectMember();
        Map<String, Object> mem = mapper.selectMemberById(username);
        String level = ""+mem.get("level");

        if(level.compareTo("user")!=0)
            role = "ROLE_ADMIN";

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(role);
        CustomUserDetails userDetails = new CustomUserDetails(username, authorities);
        return userDetails;
    }
}
