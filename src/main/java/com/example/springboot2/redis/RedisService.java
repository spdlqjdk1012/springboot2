package com.example.springboot2.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public String getRedisStringValue(String key) {
        ValueOperations< String, String > stringValueOperations = stringRedisTemplate.opsForValue();
        System.out.println("Redis key : " + key);
        System.out.println("Redis value : " + stringValueOperations.get(key));
        return stringValueOperations.get(key);
    }

    public void setRedisStringValue(String key, String value) {
        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
        setOperations.add(key, value);
    }

    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }

    public boolean isExists(String key){
        return stringRedisTemplate.hasKey(key);
    }

    // 그 외
    public void etc() {
        //ListOperation
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        listOperations.rightPush("List Key", "List Value");

        System.out.println(listOperations.range("List Key", 0, 10));

        //zSetOperation
        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
        zSetOperations.incrementScore("Sort Set Key", "Sort Set Value", 1);

        zSetOperations.rangeByScore("Sort Set Key", 0, 4).forEach(System.out::println);
    }
}
