package com.profileeM.profileeM.wallet.domain;

import com.profileeM.profileeM.card.domain.Card;
import com.profileeM.profileeM.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 필수값 누락 막음
@Table(name="wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="wallet_id")
    private Long walletId;

    @ManyToOne
    @JoinColumn(name="user_id") //foreign key (user_id) reference User (user_id)
    private User user; //참조할 테이블

    @ManyToOne
    @JoinColumn(name="card_id") //foreign key (user_id) reference User (user_id)
    private Card card; //참조할 테이블

    //그룹
}
