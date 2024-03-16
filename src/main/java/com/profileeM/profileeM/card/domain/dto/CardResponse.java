package com.profileeM.profileeM.card.domain.dto;

import com.profileeM.profileeM.card.domain.Card;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CardResponse {
    private Long cardId;
    private String name;
    private String intro;
    private String birth;
    private String major;
    private String residence;
    private String mbti;
    private String food;
    private String drink;
    private String interest;
    private String frontImageBase64; // 카드 앞면 이미지를 Base64로 저장할 필드
    private String qr; // QR 코드 링크를 저장할 필드

    public static CardResponse from(Card card) {
        CardResponse response = new CardResponse();
        response.setCardId(card.getCardId());
        response.setName(card.getName());
        response.setIntro(card.getIntro());
        response.setBirth(card.getBirth());
        response.setMajor(card.getMajor());
        response.setResidence(card.getResidence());
        response.setMbti(card.getMbti());
        response.setFood(card.getFood());
        response.setDrink(card.getDrink());
        response.setInterest(card.getInterest());
        response.setFrontImageBase64(card.getFrontImageBase64()); // 카드 앞면 이미지를 Base64로 설정
        response.setQr(card.getQr()); // QR 코드 링크 설정

        return response;
    }
}
