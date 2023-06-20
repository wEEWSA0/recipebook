package com.weewsa.recipebookv3.repository;

import com.weewsa.recipebookv3.model.redisToken.RedisToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RedisTokenRepository {
    public static final String HASH_KEY = "Product";
    @Autowired
    private RedisTemplate template;

    public RedisToken save(RedisToken token){
        template.opsForHash().put(HASH_KEY,token.getUserId(), token);
        return token;
    }

    public List<RedisToken> findAll(){
        return template.opsForHash().values(HASH_KEY);
    }

    public RedisToken findById(int id){
        return (RedisToken) template.opsForHash().get(HASH_KEY,id);
    }

    public String delete(int id){
        template.opsForHash().delete(HASH_KEY,id);
        return "removed";
    }
}
