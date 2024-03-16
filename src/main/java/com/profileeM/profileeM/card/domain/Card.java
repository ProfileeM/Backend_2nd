package com.profileeM.profileeM.card.domain;

import com.profileeM.profileeM.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter // 이거 안쓰고 수정
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 필수값 누락 막음
@Table(name="card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="card_id")
    private Long cardId;

    @ManyToOne
    @JoinColumn(name="user_id") //foreign key (user_id) reference User (user_id)
    private User user; //참조할 테이블

    private String name;

    private String intro;

    private String birth;

    private String major;

    private String residence;

    private String mbti;

    private String food;

    private String drink;

    private String interest;

    @Column(length = 1000) //qr링크
    private String qr;

    //qr 이미지를 바이트 배열로 저장
    @Lob
    @Column(name = "qr_image", columnDefinition = "BLOB")
    private byte[] qrImage;

    // 카드 앞면 이미지를 Base64로 인코딩하여 저장할 칼럼 추가
    @Column(name = "front_image", length = 2000000) // 길이는 Base64 인코딩 후 크기에 따라 조절
    private String frontImageBase64;

    // 4가지 키워드 저장을 위한 컬럼 추가
    private String keyword1;

    private String keyword2;

    private String keyword3;

    private String keyword4;

}
