package com.profileeM.profileeM.wallet.controller;

import com.profileeM.profileeM.ApiResponse;
import com.profileeM.profileeM.jwt.JwtAuthenticationProvider;
import com.profileeM.profileeM.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final WalletService walletService;

    // 지갑에 받은 프로필 카드 추가
    @PostMapping("/card/{cardId}")
    public ApiResponse<?> addCard(@PathVariable(name = "cardId") Long cardId) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(walletService.addCard(cardId, userId));
    }
}
