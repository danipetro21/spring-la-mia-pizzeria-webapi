package org.lessons.springpizzeria.controller;


import jakarta.validation.Valid;
import org.lessons.springpizzeria.model.Pizza;
import org.lessons.springpizzeria.model.SpecialOffer;
import org.lessons.springpizzeria.repository.IngredientRepository;
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

    @Autowired
    IngredientRepository ingredientRepository;


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
        return "offers/form";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("specialOffer") SpecialOffer formOffer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/offers/form";
        }
        specialOfferRepository.save(formOffer);

        return "redirect:/" + formOffer.getPizza().getId();
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        Optional<SpecialOffer> offer = specialOfferRepository.findById(id);
        if (offer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("specialOffer", offer.get());
        return "/offers/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("specialOffer") SpecialOffer formOffer, BindingResult bindingResult) {
        Optional<SpecialOffer> offetToEdit = specialOfferRepository.findById(id);
        if (offetToEdit.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        formOffer.setId(id);
        specialOfferRepository.save(formOffer);
        return "redirect:/" + formOffer.getPizza().getId();
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<SpecialOffer> offetToDelete = specialOfferRepository.findById(id);
        if (offetToDelete.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        specialOfferRepository.delete(offetToDelete.get());
        return "redirect:/" + offetToDelete.get().getPizza().getId();
    }




}
