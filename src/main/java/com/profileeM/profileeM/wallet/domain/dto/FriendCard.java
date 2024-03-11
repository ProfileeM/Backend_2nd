package com.profileeM.profileeM.wallet.domain.dto;

import com.profileeM.profileeM.card.domain.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendCard {

//    private String group_name;
    private String name;
    private String major;

    public static FriendCard of(Card card) {
        return FriendCard.builder()
                .name(card.getName())
                .major(card.getMajor())
                .build();
    }
}
