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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    //카드 생성 (등록)
    public Card createCard(CardRequest cardRequest, Long userId) throws IOException, WriterException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다."));

        // QR 코드 생성
//        String img = getQRCodeImage("qrcode", 200, 200);

        // QR 코드 이미지를 byte 배열로 저장
        byte[] qrImage = generateQRCodeImageBytes("qrcode", 200, 200);

        // 사용할 키워드들을 리스트에 추가
        List<String> keywords = new ArrayList<>();
        keywords.add(cardRequest.getMbti());
        keywords.add(cardRequest.getFood());
        keywords.add(cardRequest.getDrink());
        keywords.add(cardRequest.getInterest());

        // 키워드를 섞어줌
        Collections.shuffle(keywords);

        // 카드에 저장할 키워드 개수 설정
        int numKeywordsToUse = 4;

        // 무작위로 선택된 키워드들을 리스트에 저장
        List<String> selectedKeywords = keywords.subList(0, numKeywordsToUse);

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
                .frontImageBase64(cardRequest.getFrontImageBase64()) // 카드 앞면 이미지를 프론트에서 받아와서
                .qrImage(qrImage) // QR 코드 이미지 설정
                .keyword1(selectedKeywords.get(0)) // 첫 번째 키워드 설정
                .keyword2(selectedKeywords.get(1)) // 두 번째 키워드 설정
                .keyword3(selectedKeywords.get(2)) // 세 번째 키워드 설정
                .keyword4(selectedKeywords.get(3)) // 네 번째 키워드 설정
                .build();

        return cardRepository.save(card);
    }

    public String getQRCodeImage(String url, String key, int width, int height) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

        return Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
    }

    // QR 코드 이미지를 byte 배열로 반환하는 메서드
    public byte[] generateQRCodeImageBytes(String url, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);

        int matrixWidth = bitMatrix.getWidth();
        int matrixHeight = bitMatrix.getHeight();

        byte[] qrCodeBytes = new byte[matrixWidth * matrixHeight];

        for (int y = 0; y < matrixHeight; y++) {
            for (int x = 0; x < matrixWidth; x++) {
                qrCodeBytes[y * matrixWidth + x] = (byte) (bitMatrix.get(x, y) ? 1 : 0);
            }
        }

        return qrCodeBytes;
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
    }

    private String createQRCode(String url, Long cardId) throws IOException, WriterException {
        // 랜덤한 숫자로 키 생성
        String key = generateRandomKey();

        // QR 코드 생성
        String qrImage = getQRCodeImage(url, key, 250, 350);

        // Redis에 저장
        redisTemplate.opsForValue().set(key, String.valueOf(cardId));
        redisTemplate.expire(key, 150, TimeUnit.SECONDS); // TTL 설정 (2분 30초)

        return qrImage;
    }

    private String generateRandomKey() {
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000);
        return String.valueOf(randomNumber);
    }
}
