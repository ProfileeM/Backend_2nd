package com.profileeM.profileeM.card.repository;

import com.profileeM.profileeM.card.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}