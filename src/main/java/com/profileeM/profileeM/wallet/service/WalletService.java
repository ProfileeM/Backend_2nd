package com.profileeM.profileeM.wallet.service;

import com.profileeM.profileeM.card.repository.CardRepository;
import com.profileeM.profileeM.user.domain.UserRepository;
import com.profileeM.profileeM.wallet.domain.Wallet;
import com.profileeM.profileeM.wallet.domain.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public Wallet addCard(Long cardId, Long userId) {
        return walletRepository.save(Wallet.builder()
                        .card(cardRepository.findByCardId(cardId))
                        .user(userRepository.findByUserId(userId))
                        .build());
    }
}
