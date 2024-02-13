package com.profileeM.profileeM.user.service;

import com.profileeM.profileeM.client.KakaoClient;
import com.profileeM.profileeM.jwt.JwtAuthenticationProvider;
import com.profileeM.profileeM.user.domain.User;
import com.profileeM.profileeM.user.domain.UserRepository;
import com.profileeM.profileeM.user.domain.dto.LoginResponse;
import com.profileeM.profileeM.user.domain.params.KakaoInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OAuthService {
    private final KakaoClient kakaoClient;
    private final UserRepository userRepository;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public LoginResponse loginKakao(String authorizationCode) {
        String accessToken = kakaoClient.requestAccessToken(authorizationCode);
        KakaoInfoResponse info = kakaoClient.requestKakaoInfo(accessToken);

        Long userId = findOrCreateMember(info);

        LoginResponse response = LoginResponse.builder()
                .id(userId)
                .name(info.getProperties().getNickname())
                .email(info.getKakao_account().getEmail())
                .accessToken(jwtAuthenticationProvider.createAccessToken(userId, info.getProperties().getNickname()))
                .refreshToken(jwtAuthenticationProvider.createRefreshToken(userId, info.getProperties().getNickname()))
                .build();

        return response;
    }

    private Long findOrCreateMember(KakaoInfoResponse info) {
        return userRepository.findByEmail(info.getKakao_account().getEmail())
                .map(User::getUserId)
                .orElseGet(() -> newMember(info));
    }

    private Long newMember(KakaoInfoResponse info) {
        User user = User.builder()
                .userId(info.getId())
                .email(info.getKakao_account().getEmail())
                .name(info.getProperties().getNickname())
                .build();

        userRepository.save(user);

        return user.getUserId();
    }
}
