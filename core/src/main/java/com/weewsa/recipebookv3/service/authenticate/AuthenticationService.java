package com.weewsa.recipebookv3.service.authenticate;

import com.weewsa.recipebookv3.model.refreshToken.RefreshToken;
import com.weewsa.recipebookv3.model.user.Role;
import com.weewsa.recipebookv3.model.user.User;
import com.weewsa.recipebookv3.repository.RefreshTokenRepository;
import com.weewsa.recipebookv3.service.authenticate.dto.AuthenticationRequest;
import com.weewsa.recipebookv3.service.authenticate.dto.AuthenticationResponse;
import com.weewsa.recipebookv3.service.authenticate.dto.RegisterRequest;
import com.weewsa.recipebookv3.service.authenticate.exception.InvalidToken;
import com.weewsa.recipebookv3.service.user.UserService;
import com.weewsa.recipebookv3.service.user.exception.UserAlreadyExists;
import com.weewsa.recipebookv3.service.user.exception.UserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    @Value("${application.security.jwt.expiration}")
    private Duration ACCESS_TOKEN_EXPIRATION;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private Duration REFRESH_TOKEN_EXPIRATION;
    private final JWTService jwtService;
    private final UserService userService;
    private final RefreshTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request) throws UserAlreadyExists, UserNotFound {
        var isExist = userService.existsByLogin(request.getLogin());

        if (isExist) {
            throw new UserAlreadyExists("User already exists");
        }

        User registerUser = User.builder()
                .login(request.getLogin())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .aboutMe(request.getAboutMe())
                .role(Role.USER)
                .build();

        userService.save(registerUser);

        return createNewUserTokens(registerUser.getLogin(), registerUser.getRole());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserNotFound {
        var user = userService.getByLogin(request.getLogin());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserNotFound("User not found");
        }

        return createNewUserTokens(user.getLogin(), user.getRole());
    }

    public AuthenticationResponse authenticateByRefreshToken(String refreshToken) throws InvalidToken, UserNotFound {
        var foundToken = tokenRepository.findByToken(refreshToken);

        if (foundToken.isEmpty()) {
            throw new InvalidToken("Invalid refresh token");
        }

        User user = userService.getById(foundToken.get().getUserId());

        return createNewUserTokens(user.getLogin(), user.getRole());
    }

    private AuthenticationResponse createNewUserTokens(String login, Role role) throws UserNotFound {
        String refreshToken = jwtService.generateRefreshToken(login, REFRESH_TOKEN_EXPIRATION);

        var userId = userService.getIdByLogin(login);

        tokenRepository.save(RefreshToken.builder()
                .token(refreshToken)
                .userId(userId)
                .build());

        Map<String, Object> accessTokenClaims = new HashMap<>();
        accessTokenClaims.put(JWTService.ROLE, role.name());
        String accessToken = jwtService.generateAccessToken(login, accessTokenClaims, ACCESS_TOKEN_EXPIRATION);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
