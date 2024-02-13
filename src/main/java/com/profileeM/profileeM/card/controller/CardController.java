package com.profileeM.profileeM.card.controller;

import com.profileeM.profileeM.ApiResponse;
import com.profileeM.profileeM.card.domain.Card;
import com.profileeM.profileeM.card.domain.dto.*;
import com.profileeM.profileeM.card.service.CardService;
import com.profileeM.profileeM.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;


    @PostMapping//내 프로필 카드 등록
    public ResponseEntity<ApiResponse<CardResponse>> createCard(@RequestBody CardRequest cardRequest) {
        // QR 코드 생성은 서비스 레이어 내부에서 처리
        Long userId = jwtAuthenticationProvider.getUserId();

        Card card = cardService.createCard(cardRequest, userId);
        CardResponse cardResponse = CardResponse.from(card);
        ApiResponse<CardResponse> apiResponse = ApiResponse.<CardResponse>ok(cardResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @DeleteMapping("/{cardId}") //내 프로필 카드 삭제
    public ResponseEntity<ApiResponse<Void>> deleteCard(@PathVariable Long cardId) {

        cardService.deleteCard(cardId);
        ApiResponse<Void> response = ApiResponse.ok();
        response.setMessage("카드 삭제 성공");
        return ResponseEntity.ok(response);
    }

    @GetMapping // 내 프로필 카드 전체 조회
    public ResponseEntity<ApiResponse<List<CardResponse>>> getAllCards() {
        Long userId = jwtAuthenticationProvider.getUserId(); // JWT에서 userId 추출
        List<CardResponse> cards = cardService.findAllCardsByUserId(userId)
                .stream()
                .map(CardResponse::from)
                .collect(Collectors.toList());
        ApiResponse<List<CardResponse>> response = ApiResponse.ok(cards);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{cardId}") //내 프로필 특정 조회
    public ResponseEntity<ApiResponse<CardResponse>> getCardById(@PathVariable Long cardId) {
        Optional<Card> card = cardService.findCardById(cardId);
        if (card.isPresent()) {
            CardResponse cardResponse = CardResponse.from((Card) ((Optional<?>) card).get());
            return ResponseEntity.ok(ApiResponse.ok(cardResponse));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{cardId}") //내 프로필 카드 수정
    public ResponseEntity<ApiResponse<CardResponse>> updateCard(@PathVariable Long cardId, @RequestBody CardRequest cardRequest) {
        CardResponse cardResponse = cardService.updateCard(cardId, cardRequest);
        if (cardResponse != null) {
            return ResponseEntity.ok(ApiResponse.ok(cardResponse));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
