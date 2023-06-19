package com.weewsa.recipebookv3.controller;

import com.weewsa.recipebookv3.model.user.Role;
import com.weewsa.recipebookv3.service.authenticate.AuthenticationService;
import com.weewsa.recipebookv3.service.authenticate.JWTService;
import com.weewsa.recipebookv3.service.authenticate.dto.AuthenticationRequest;
import com.weewsa.recipebookv3.service.authenticate.dto.AuthenticationResponse;
import com.weewsa.recipebookv3.service.authenticate.dto.RefreshTokenRequest;
import com.weewsa.recipebookv3.service.authenticate.dto.RegisterRequest;
import com.weewsa.recipebookv3.service.authenticate.exception.InvalidToken;
import com.weewsa.recipebookv3.service.authenticate.exception.NotAuthorized;
import com.weewsa.recipebookv3.service.authenticate.exception.NotEnoughRights;
import com.weewsa.recipebookv3.service.user.UserService;
import com.weewsa.recipebookv3.service.user.exception.UserAlreadyExists;
import com.weewsa.recipebookv3.service.user.exception.UserNotFound;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;
    private final JWTService jwtService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> isWorking() {
        return ResponseEntity.ok("working!");
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        AuthenticationResponse response;

        try {
            response = authenticationService.register(registerRequest);
        } catch (UserAlreadyExists e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse response;

        try {
            response = authenticationService.authenticate(authenticationRequest);
        } catch (UserNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("authenticate-by-refresh-token")
    public ResponseEntity<?> authenticate(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        AuthenticationResponse response;

        try {
            response = authenticationService.authenticateByRefreshToken(refreshTokenRequest.getRefreshToken());
        } catch (InvalidToken | UserNotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("edit")
    public ResponseEntity<String> edit(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) {
        try {
            var claims = jwtService.getClaimsIfHasUserAccess(request);

            userService.edit(claims.getSubject(), registerRequest);
        } catch (UserNotFound | NotAuthorized | NotEnoughRights | InvalidToken e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }

        return ResponseEntity.ok("Saved");
    }
}
