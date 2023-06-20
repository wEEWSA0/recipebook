package com.weewsa.recipebookv3.controller;

import com.weewsa.recipebookv3.model.redisToken.RedisToken;
import com.weewsa.recipebookv3.repository.RedisTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/redis")
@RequiredArgsConstructor
public class TestRedisController {
    private final RedisTokenRepository redisTokenRepository;

    @PostMapping
    public RedisToken save(@RequestBody RedisToken token) {
        return redisTokenRepository.save(token);
    }

    @GetMapping
    public List<RedisToken> getAll() {
        return redisTokenRepository.findAll();
    }

    @GetMapping("/{id}")
    public RedisToken find(@PathVariable int id) {
        return redisTokenRepository.findById(id);
    }
    @DeleteMapping("/{id}")
    public String remove(@PathVariable int id)   {
        return redisTokenRepository.delete(id);
    }
}
