package com.profileeM.profileeM.card.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.profileeM.profileeM.card.domain.Card;
import com.profileeM.profileeM.card.domain.dto.CardRequest;
import com.profileeM.profileeM.card.domain.dto.CardResponse;
import com.profileeM.profileeM.card.repository.CardRepository;
import com.profileeM.profileeM.user.domain.User;
import com.profileeM.profileeM.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    public Card createCard(CardRequest cardRequest, Long userId) throws IOException, WriterException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다."));

        // QR 코드 생성
        String img = getQRCodeImage("qrcode", 200, 200);

        Card card = Card.builder()
                .user(user) // User 엔티티 설정
                .name(cardRequest.getName())
                .intro(cardRequest.getIntro())
                .birth(cardRequest.getBirth())
                .major(cardRequest.getMajor())
                .residence(cardRequest.getResidence())
                .mbti(cardRequest.getMbti())
                .food(cardRequest.getFood())
                .drink(cardRequest.getDrink())
                .interest(cardRequest.getInterest())
                .qr(img) // QR 코드 설정
                .theme(cardRequest.getTheme())
                .build();

        return cardRepository.save(card);
    }

    public String getQRCodeImage(String url, int width, int height) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        return Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
    }

    public void deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);

    }

    // 사용자 ID에 해당하는 카드를 모두 조회
    public List<Card> findAllCardsByUserId(Long userId) {
        return cardRepository.findByUserUserId(userId);
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

