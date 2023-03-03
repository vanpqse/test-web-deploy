package com.example.user_web_service.controller;

import com.example.user_web_service.dto.ResponseObject;
import com.example.user_web_service.entity.User;
import com.example.user_web_service.form.GoogleLoginRequest;
import com.example.user_web_service.form.GoogleLoginResponse;
import com.example.user_web_service.repository.UserRepository;
import com.example.user_web_service.security.jwt.GameTokenProvider;
import com.example.user_web_service.security.jwt.JwtProvider;
import com.example.user_web_service.security.jwt.JwtResponse;
import com.example.user_web_service.security.jwt.RefreshTokenProvider;
import com.example.user_web_service.security.userprincipal.Principal;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/login")
public class GoogleLoginController {
    private final String companyEmail = "FA.HCM@fsoft.com.vn";
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtTokenProvider;
    @Autowired
    private RefreshTokenProvider refreshTokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameTokenProvider gameTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/google")
    public ResponseEntity<?> authenticateUser(@RequestBody GoogleLoginRequest loginRequest) {

        // Lấy thông tin từ Google OAuth2
        String email = loginRequest.getEmail();
        String idToken = loginRequest.getIdToken();

        // Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu hay chưa
        User existingUser = userRepository.findByEmail(email);
        if (existingUser !=null) {
            return ResponseEntity.badRequest().body(new GoogleLoginResponse(false, "Email not found"));
        }
        try {
            // Xác thực người dùng
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, idToken)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Principal userPrinciple = (Principal) authentication.getPrincipal();
            String accessToken = jwtTokenProvider.createToken(userPrinciple);
            String refreshToken = refreshTokenProvider.createRefreshToken(loginRequest.getEmail()).getToken();
            String gameToken = gameTokenProvider.createGameToken(loginRequest.getEmail()).getToken();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseObject(HttpStatus.ACCEPTED.toString(), "Login success!", null, new JwtResponse(accessToken, refreshToken, gameToken)));
        } catch (AuthenticationException e) {
            if (e instanceof DisabledException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Account has been locked. Please contact " + companyEmail + " for more information", null, null));
            }
            if(e instanceof AccountExpiredException){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "The account has expired. Please contact " + companyEmail + " for more information", null, null));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Invalid email or password. Please try again.", null, null));
        }
    }
}

