package com.profileeM.profileeM.user.controller;

import com.profileeM.profileeM.user.domain.dto.LoginResponse;
import com.profileeM.profileeM.user.service.OAuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final OAuthService oAuthService;

    // 카카오 로그인
    @PostMapping("/oauth/kakao/login")
    public LoginResponse loginKakao(@RequestParam("code") String authorizationCode) {
        return oAuthService.loginKakao(authorizationCode);
    }
}
