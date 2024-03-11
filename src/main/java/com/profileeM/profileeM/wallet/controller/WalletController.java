package com.profileeM.profileeM.wallet.controller;

import com.profileeM.profileeM.ApiResponse;
import com.profileeM.profileeM.jwt.JwtAuthenticationProvider;
import com.profileeM.profileeM.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    // 지갑에서 프로필 카드 삭제
    @DeleteMapping("/card/{cardId}")
    public ApiResponse<?> removeCard(@PathVariable(name = "cardId") Long cardId) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(walletService.removeCard(cardId, userId));
    }
}
