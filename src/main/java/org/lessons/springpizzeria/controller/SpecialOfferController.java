package org.lessons.springpizzeria.controller;


import jakarta.validation.Valid;
import org.lessons.springpizzeria.model.Pizza;
import org.lessons.springpizzeria.model.SpecialOffer;
import org.lessons.springpizzeria.repository.PizzaRepository;
import org.lessons.springpizzeria.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offers")
public class SpecialOfferController {

    @Autowired
    PizzaRepository pizzaRepository;
    @Autowired
    SpecialOfferRepository specialOfferRepository;


    @GetMapping("/create")
    public String create(@RequestParam("pizzaId") Integer pizzaId, Model model) {
        SpecialOffer offer = new SpecialOffer();
        offer.setStart(LocalDate.now());
        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);
        if (pizza.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "pizza with id " + pizzaId + " not found");
        }
        offer.setPizza(pizza.get());
        model.addAttribute("specialOffer", offer);
        return "/offers/form";
    }


    @PostMapping("/create")
    public String update(@Valid @ModelAttribute("specialOffer") SpecialOffer formOffer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/offers/form";
        }
        specialOfferRepository.save(formOffer);

        return "redirect:/" + formOffer.getPizza().getId();
    }

}
