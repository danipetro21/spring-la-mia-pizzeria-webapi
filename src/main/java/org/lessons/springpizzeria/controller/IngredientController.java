package org.lessons.springpizzeria.controller;


import jakarta.validation.Valid;
import org.lessons.springpizzeria.model.Ingredient;
import org.lessons.springpizzeria.model.Pizza;
import org.lessons.springpizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {


    @Autowired
    private IngredientRepository ingredientRepository;


    @GetMapping
    public String list(Model model) {
        List<Ingredient> ingredients;
        ingredients = ingredientRepository.findAll();
        model.addAttribute("listIngredient", ingredients);
        return "/ingredients/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Ingredient ingredientToDelete = getIngredientById(id);

        for (Pizza pizza : ingredientToDelete.getPizze()) {
            pizza.getIngredients().remove(ingredientToDelete);
        }

        ingredientRepository.delete(ingredientToDelete);
        return "redirect:/ingredients";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ingredient") Ingredient formIngredient) {
        ingredientRepository.save(formIngredient);
        return "redirect:/ingredients";
    }




    private Ingredient getIngredientById(Integer id) {
        Optional<Ingredient> result = ingredientRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ingredient with id " + id + " not found");
        }
        return result.get();
    }
}
