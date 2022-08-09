package com.example.springboot2.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/login")
public class RedisController {
    @Autowired
    private RedisService redisService;
    @PostMapping(value = "/getRedisStringValue") public void getRedisStringValue(String key) {
        redisService.getRedisStringValue(key);
    }

    @GetMapping(value = "/redisSetTest")
    public String main(){
        redisService.setRedisStringValue("운동", "축구");
        return "test";
    }
}
