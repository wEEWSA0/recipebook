package com.weewsa.recipebookv3.repository;

import com.weewsa.recipebookv3.model.redisToken.RedisToken;
import com.weewsa.recipebookv3.model.refreshToken.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
    public static final String HASH_KEY = "RefreshToken";

    private final RedisTemplate<String, RefreshToken> template;

    public RefreshToken save(RefreshToken token){
        template.opsForHash().put(HASH_KEY, token.getToken(), token);
        return token;
    }

    public Optional<RefreshToken> findByToken(String token){
        RefreshToken refreshToken = (RefreshToken) template.opsForHash().get(HASH_KEY, token);
        return Optional.ofNullable(refreshToken);
    }
}
