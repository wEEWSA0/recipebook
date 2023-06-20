package com.weewsa.recipebookv3.model.redisToken;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Token")
public class RedisToken implements Serializable {
    @Id
    private Long userId;
    private String token;
}
