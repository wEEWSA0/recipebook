package com.weewsa.recipebookv3.service.authenticate;

import com.weewsa.recipebookv3.model.user.Role;
import com.weewsa.recipebookv3.service.authenticate.exception.InvalidToken;
import com.weewsa.recipebookv3.service.authenticate.exception.NotAuthorized;
import com.weewsa.recipebookv3.service.authenticate.exception.NotEnoughRights;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JWTService {
    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;
    public static final String ROLE = "role";

    public String generateAccessToken(String login, Map<String, Object> claims, Duration expiration) {
        Instant now = Instant.now();
        Date expiryDate = Date.from(now.plus(expiration));

        return Jwts.builder()
                .setSubject(login)
                .addClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String generateRefreshToken(String login, Duration expiration) {
        Instant now = Instant.now();
        Date expiryDate = Date.from(now.plus(expiration));

        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(Date.from(now))
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Claims parseToken(String token) throws InvalidToken {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new InvalidToken("Invalid token");
        }
    }

    public Claims getClaimsFromRequest(HttpServletRequest request) throws NotAuthorized, InvalidToken {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorized("Not authorized");
        }

        String token = authorizationHeader.substring(7);

        return parseToken(token);
    }

    public boolean hasAccess(Claims claims, Role role) {
        var roleFromToken = Role.valueOf((String) claims.get(JWTService.ROLE));

        return roleFromToken == role;
    }

    public boolean hasAccessFromRequest(HttpServletRequest request, Role role) throws NotAuthorized, InvalidToken {
        var claims = getClaimsFromRequest(request);

        return hasAccess(claims, role);
    }

    public Claims getClaimsIfHasUserAccess(HttpServletRequest request) throws NotEnoughRights, InvalidToken, NotAuthorized {
        return getClaimsIfHasAccess(request, Role.USER);
    }

    public Claims getClaimsIfHasAccess(HttpServletRequest request, Role role) throws NotEnoughRights, InvalidToken, NotAuthorized {
        var claims = getClaimsFromRequest(request);
        if (!hasAccess(claims, Role.USER)) {
            throw new NotEnoughRights("Not enough rights");
        }

        return claims;
    }
}
