package org.lessons.springpizzeria.controller;


import org.lessons.springpizzeria.model.Pizza;
import org.lessons.springpizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;


    @GetMapping
    public String list(
            @RequestParam(name = "keyword", required = false) String searchString,
            Model model) { // Model è la mappa di attributi che il controller passa alla view
        List<Pizza> pizze;

        if (searchString == null || searchString.isBlank()) {
            // se non ho il parametro searchString faccio la query generica
            // recupero la lista di libri dal database
            pizze = pizzaRepository.findAll();
        } else {
            // se ho il parametro searchString faccio la query con filtro
            // books = bookRepository.findByTitle(searchString);
            pizze = pizzaRepository.findByNameContainingIgnoreCase(searchString);
        }

        // passo la lista dei libri alla view
        model.addAttribute("pizzaList", pizze);
        model.addAttribute("searchInput", searchString == null ? "" : searchString);
        // restituisco il nome del template della view
        return "/list";
    }


    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer pizzaId, Model model) {
        // cerca su database i dettagli del libro con quell'id
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            // passa il libro alla view
            model.addAttribute("pizza", result.get());
            // ritorna il nome del template della view
            return "/detail";
        } else {
            // ritorno un HTTP Status 404 Not Found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "pizza with id " + pizzaId + " not found");
        }
    }
}
