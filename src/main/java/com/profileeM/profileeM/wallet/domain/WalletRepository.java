package com.profileeM.profileeM.wallet.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByCardCardIdAndUserUserId(Long cardId, Long userId);
    List<Wallet> findByUserUserId(Long userId);
}
