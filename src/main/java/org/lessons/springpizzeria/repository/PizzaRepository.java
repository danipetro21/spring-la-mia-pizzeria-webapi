package org.lessons.springpizzeria.repository;

import org.lessons.springpizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository <Pizza, Integer>{
}
