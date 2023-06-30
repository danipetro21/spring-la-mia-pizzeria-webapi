package org.lessons.springpizzeria.repository;

import org.lessons.springpizzeria.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
