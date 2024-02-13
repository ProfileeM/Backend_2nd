package com.profileeM.profileeM.card.service;

import com.profileeM.profileeM.card.domain.Card;
import com.profileeM.profileeM.card.domain.dto.CardRequest;
import com.profileeM.profileeM.card.domain.dto.CardResponse;
import com.profileeM.profileeM.card.repository.CardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CardService {

    private final CardRepository cardRepository;
    public Card createCard(CardRequest cardRequest) {
        // QR 코드 생성 로직 (예시) => 수정
        String qrCode = "GeneratedQRCode-" + System.currentTimeMillis();

        Card card = Card.builder()
                .user(cardRequest.getUser())
                .name(cardRequest.getName())
                .intro(cardRequest.getIntro())
                .birth(cardRequest.getBirth())
                .major(cardRequest.getMajor())
                .residence(cardRequest.getResidence())
                .mbti(cardRequest.getMbti())
                .food(cardRequest.getFood())
                .drink(cardRequest.getDrink())
                .interest(cardRequest.getInterest())
                .qr(qrCode) // QR 코드 설정
                .theme(cardRequest.getTheme())
                .build();

        return cardRepository.save(card);
    }

    public void deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);

    }

    public List<Card> findAllCards() {
        return cardRepository.findAll();
    }

    public Optional<Card> findCardById(Long cardId) {
        return cardRepository.findById(cardId);
    }

    @Transactional
    public CardResponse updateCard(Long cardId, CardRequest cardRequest) {
        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            // 카드 정보 업데이트
            updateCardDetails(card, cardRequest);
            cardRepository.save(card);
            return CardResponse.from(card);
        }
        return null;
    }

    private void updateCardDetails(Card card, CardRequest cardRequest) {
        // Card 엔티티의 필드를 CardRequest로부터 받은 값으로 업데이트
        card.setName(cardRequest.getName());
        card.setIntro(cardRequest.getIntro());
        card.setBirth(cardRequest.getBirth());
        card.setMajor(cardRequest.getMajor());
        card.setResidence(cardRequest.getResidence());
        card.setMbti(cardRequest.getMbti());
        card.setFood(cardRequest.getFood());
        card.setDrink(cardRequest.getDrink());
        card.setInterest(cardRequest.getInterest());
        card.setTheme(cardRequest.getTheme());

    }
}

