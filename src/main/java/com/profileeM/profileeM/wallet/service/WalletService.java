package com.profileeM.profileeM.wallet.service;

import com.profileeM.profileeM.card.repository.CardRepository;
import com.profileeM.profileeM.user.domain.UserRepository;
import com.profileeM.profileeM.wallet.domain.Wallet;
import com.profileeM.profileeM.wallet.domain.WalletRepository;
import com.profileeM.profileeM.wallet.domain.dto.FriendCard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public String removeCard(Long cardId, Long userId) {
        Wallet remove = walletRepository.findByCardCardIdAndUserUserId(cardId, userId);
        walletRepository.delete(remove);
        return "삭제완료";
    }

    public List<FriendCard> getFriendCards(Long userId) {
        List<Wallet> profileCards = walletRepository.findByUserUserId(userId);

        List<FriendCard> friendCardList = new ArrayList<>();

        for (Wallet card : profileCards) {
            Long cardId = card.getCard().getCardId();

            friendCardList.add(FriendCard.of(cardRepository.findByCardId(cardId)));
        }

        return friendCardList;
    }
}
