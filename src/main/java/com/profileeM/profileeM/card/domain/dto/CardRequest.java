package com.profileeM.profileeM.card.domain.dto;

import com.profileeM.profileeM.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CardRequest {
    private User user; // Simplified for example; typically, you'd pass userId and resolve User entity
    private String name;
    private String intro;
    private String birth;
    private String major;
    private String residence;
    private String mbti;
    private String food;
    private String drink;
    private String interest;
    private String qr;
    private int theme;
}
