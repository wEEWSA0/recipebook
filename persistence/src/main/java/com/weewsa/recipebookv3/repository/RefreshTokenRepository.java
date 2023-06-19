package com.weewsa.recipebookv3.repository;

import com.weewsa.recipebookv3.model.refreshToken.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findRefreshTokenByToken(String token);
}
