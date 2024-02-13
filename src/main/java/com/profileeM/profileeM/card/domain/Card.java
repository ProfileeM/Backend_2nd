package com.profileeM.profileeM.card.domain;

import com.profileeM.profileeM.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
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

    @Column(length = 1000)
    private String qr;

    private int theme;
}
