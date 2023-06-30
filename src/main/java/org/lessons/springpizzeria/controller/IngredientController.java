package org.lessons.springpizzeria.controller;


import org.lessons.springpizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {


    @Autowired
    private IngredientRepository ingredientRepository;


}
