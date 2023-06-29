package org.lessons.springpizzeria.controller;


import org.lessons.springpizzeria.repository.PizzaRepository;
import org.lessons.springpizzeria.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offers")
public class SpecialOfferController{

    @Autowired
    PizzaRepository pizzaRepository;
    @Autowired
    SpecialOfferRepository specialOfferRepository;




}
