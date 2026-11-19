package com.mallcloud.auth.service;

import com.mallcloud.api.client.UserClient; import com.mallcloud.api.user.UserInternalView;
import com.mallcloud.auth.dto.LoginRequest; import com.mallcloud.auth.dto.TokenResponse;
import com.mallcloud.common.error.BizException; import com.mallcloud.common.error.ErrorCode;
import com.mallcloud.common.security.AuthenticatedUser; import com.mallcloud.common.security.JwtService;
import feign.FeignException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserClient userClient; private final JwtService jwtService; private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public AuthService(UserClient userClient, JwtService jwtService) { this.userClient = userClient; this.jwtService = jwtService; }
    public TokenResponse login(LoginRequest request) {
        UserInternalView user;
        try { user = userClient.findByUsername(request.username()); } catch (FeignException e) { throw new BizException(ErrorCode.LOGIN_FAILED); }
        if (user == null || user.status() != 1 || !passwordEncoder.matches(request.password(), user.passwordHash())) throw new BizException(ErrorCode.LOGIN_FAILED);
        AuthenticatedUser principal = new AuthenticatedUser(user.id(), user.username(), user.role());
        return new TokenResponse(jwtService.createToken(principal), "Bearer", jwtService.expiresIn(), user.id(), user.username(), user.role());
    }
}
