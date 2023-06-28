package org.lessons.springpizzeria.controller;


import jakarta.validation.Valid;
import org.lessons.springpizzeria.model.Pizza;
import org.lessons.springpizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;


    @GetMapping
    public String list(@RequestParam(name = "keyword", required = false) String searchString, Model model) {
        List<Pizza> pizze;

        if (searchString == null || searchString.isBlank()) {
            pizze = pizzaRepository.findAll();
        } else {
            pizze = pizzaRepository.findByNameContainingIgnoreCase(searchString);
        }

        model.addAttribute("pizzaList", pizze);
        model.addAttribute("searchInput", searchString == null ? "" : searchString);
        return "/list";
    }


    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer pizzaId, Model model) {
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            model.addAttribute("pizza", result.get());
            return "/detail";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza with id " + pizzaId + " not found");
        }
    }


    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formBook, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/create";
        }

        pizzaRepository.save(formBook);

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Pizza pizzaToDelete = getPizzaById(id);
        pizzaRepository.delete(pizzaToDelete);
        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Pizza pizzaToEdit = getPizzaById(id);
        model.addAttribute("pizza", pizzaToEdit);
        return "/create";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Pizza pizzaToEdit = getPizzaById(id);
        formPizza.setId(pizzaToEdit.getId());

        if (bindingResult.hasErrors()) {
            return "/create";
        }

        pizzaRepository.save(formPizza);
        return "redirect:/";
    }


    private Pizza getPizzaById(Integer id) {
        // verificare se esiste un book con quell'id
        Optional<Pizza> result = pizzaRepository.findById(id);
        // se non esiste ritorno un http 404
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza with id " + id + " not found");
        }
        return result.get();
    }
}
