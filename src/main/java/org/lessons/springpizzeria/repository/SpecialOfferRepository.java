package org.lessons.springpizzeria.repository;

import org.lessons.springpizzeria.model.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Integer> {
}
