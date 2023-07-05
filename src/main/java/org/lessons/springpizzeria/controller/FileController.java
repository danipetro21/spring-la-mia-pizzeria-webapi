package org.lessons.springpizzeria.controller;


import org.lessons.springpizzeria.model.Pizza;
import org.lessons.springpizzeria.repository.PizzaRepository;
import org.lessons.springpizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files")
public class FileController {


    @Autowired
    PizzaService pizzaService;

    @GetMapping("/photo/{pizzaId}")
    public ResponseEntity<byte[]> getPizzaPhoto(@PathVariable Integer pizzaId) {
            Pizza pizza = pizzaService.getById(pizzaId);
            MediaType mediaType = MediaType.IMAGE_JPEG;
            return ResponseEntity.ok().contentType(mediaType).body(pizza.getPhoto());
    }
}
