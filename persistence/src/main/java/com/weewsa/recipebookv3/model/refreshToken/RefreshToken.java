package com.weewsa.recipebookv3.model.refreshToken;

import jakarta.persistence.*;
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
@RedisHash("RefreshToken")
public class RefreshToken implements Serializable {
    @Id
    private String token;
    private Long userId;
}
