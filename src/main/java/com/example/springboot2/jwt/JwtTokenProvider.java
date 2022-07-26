package com.example.springboot2.jwt;

import com.example.springboot2.mapper.MemberMapper;
import com.example.springboot2.redis.RedisService;
import com.example.springboot2.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private String secretKey = "secretKey_";
    private long rTokenValidTime = 30 * 60 * 1000L;
    private long tokenValidTime = 1000;
    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    private RedisService redisService;

    @Autowired
    public MemberMapper mapper;

    public TokenResponse createToken(String userPk, String midx, HttpServletRequest request) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        Date now = new Date();
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + rTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        //이미 토큰이 발행되어 있다면 기존 토큰 삭제
        deleteToken(userPk, request);
        // 디비에 삽입
        //mapper.insertToken(userPk, refreshToken, accessToken, midx, userPk);
        redisService.setRedisStringValue(userPk, refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void deleteToken(String userPk, HttpServletRequest request){
        /*Map<String, Object> authInfo = mapper.selectTokenByMemidx(midx);
        if(authInfo==null)
            return;*/

        Cookie[] cookies=request.getCookies(); // 모든 쿠키 가져오기
        if(cookies!=null){
            for (Cookie c : cookies) {
                String name = c.getName(); // 쿠키 이름 가져오기
                String value = c.getValue(); // 쿠키 값 가져오기
                if (name.equals("jwt")) {
                    c.setMaxAge(0);
                    if(redisService.isExists(userPk))
                        redisService.delete(userPk);
                    break;
                }
            }
        }
        //mapper.deleteToken(midx);
    }
    public String getRefreshToken(String token){
        //토큰 해독 이름으로 조회
 /*       Map<String, Object> authInfo = mapper.selectTokenByAToken(token);
        if(authInfo==null)
            return null;
        String refresh = ""+authInfo.get("refresh");*/
        System.out.println("token userPk:"+getUserPk(token));
        String userPk = getUserPk(token);
        String refresh = null;
        if(redisService.isExists(userPk))
            refresh = redisService.getRedisStringValue(userPk);
        return refresh;
    }

    public String setAccessToken(String access, String refresh, HttpServletRequest request, ServletResponse response){
        String userPk = getUserPk(refresh);
        // access refresh userPk 동일한 auth값 있는지 디비에서 체크
//        Map<String, Object> authInfo = mapper.selectAuth(userPk, access, refresh);
//        if (authInfo==null)
//            return null;
        if(!redisService.isExists(userPk))
            return null;

        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        Date now = new Date();
        String accessToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + rTokenValidTime)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과
                // signature 에 들어갈 secret값 세팅
                .compact();
        // accessToken, refreshToken 디비 갱신, 쿠키갱신
        //String midx = ""+authInfo.get("memidx");
        //mapper.updateToken(midx, accessToken, refreshToken);
        redisService.delete(userPk);
        redisService.setRedisStringValue(userPk, refreshToken);

        Cookie cookie = new Cookie("jwt", accessToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        ((HttpServletResponse) response).addCookie(cookie);
        return accessToken;
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        System.out.println("getAuthentication token:"+this.getUserPk(token));
        CustomUserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserPk(token));//디비조회

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옵니다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        Cookie[] cookies=request.getCookies(); // 모든 쿠키 가져오기
        if(cookies!=null){
            for (Cookie c : cookies) {
                String name = c.getName(); // 쿠키 이름 가져오기
                String value = c.getValue(); // 쿠키 값 가져오기
                if (name.equals("jwt")) {
                    return value;
                }
            }
        }
        return null;
        //return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
