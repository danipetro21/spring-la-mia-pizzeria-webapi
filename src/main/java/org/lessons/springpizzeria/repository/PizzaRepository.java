package org.lessons.springpizzeria.repository;

import org.lessons.springpizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository <Pizza, Integer>{

    List<Pizza> findByNameContainingIgnoreCase(String name);



}
