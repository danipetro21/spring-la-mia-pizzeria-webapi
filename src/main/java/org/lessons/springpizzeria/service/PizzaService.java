package org.lessons.springpizzeria.service;


import org.lessons.springpizzeria.dto.PizzaForm;
import org.lessons.springpizzeria.model.Pizza;
import org.lessons.springpizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {


    @Autowired
    PizzaRepository pizzaRepository;


    public List<Pizza> getAll(Optional<String> keywordOpt) {
        if (keywordOpt.isEmpty()) {
            return pizzaRepository.findAll();
        } else {
            return pizzaRepository.findByNameContainingIgnoreCase(keywordOpt.get());
        }
    }

    public Pizza getById(Integer id) throws RuntimeException {
        Optional<Pizza> pizzaOpt = pizzaRepository.findById(id);
        if (pizzaOpt.isPresent()) {
            return pizzaOpt.get();
        } else {
            throw new RuntimeException("Book with id " + id);
        }
    }

    public Pizza create(Pizza pizza) {
        Pizza pizzaToPersist = new Pizza();

        pizzaToPersist.setName(pizza.getName());
        pizzaToPersist.setDescr(pizza.getDescr());
        pizzaToPersist.setPhoto(pizza.getPhoto());
        pizzaToPersist.setPrice(pizza.getPrice());

        return pizzaRepository.save(pizzaToPersist);
    }

    public Pizza create(PizzaForm pizzaForm) {
        // converto il PizzaForm in un Pizza
        Pizza pizza = mapPizzaFormToPizza(pizzaForm);

        return create(pizza);
    }

    public Pizza update(PizzaForm pizzaForm){
        Pizza pizza = mapPizzaFormToPizza(pizzaForm);
        Pizza pizzaDb = getById(pizza.getId());
        pizzaDb.setName(pizza.getName());
        pizzaDb.setDescr(pizza.getDescr());
        pizzaDb.setPhoto(pizza.getPhoto());
        pizzaDb.setPrice(pizza.getPrice());

        return pizzaRepository.save(pizzaDb);
    }
    public PizzaForm getPizzaFormById(Integer id) {
        Pizza pizza = getById(id);
        return mapPizzaToPizzaForm(pizza);
    }

    private PizzaForm mapPizzaToPizzaForm(Pizza pizza) {
        // creo un nuovo BookForm vuoto
        PizzaForm pizzaForm = new PizzaForm();
        // copio i campi con corrispondenza esatta
        pizzaForm.setName(pizza.getName());
        pizzaForm.setDescr(pizza.getDescr());
        pizzaForm.setPrice(pizza.getPrice());

        return pizzaForm;
    }

    private Pizza mapPizzaFormToPizza(PizzaForm pizzaForm) {
        Pizza pizza = new Pizza();
        pizza.setName(pizza.getName());
        pizza.setDescr(pizza.getDescr());
        pizza.setPrice(pizza.getPrice());

        byte[] coverBytes = multipartFileToByteArray(pizzaForm.getPhotoFile());
        pizza.setPhoto(coverBytes);

        return pizza;
    }


    private byte[] multipartFileToByteArray(MultipartFile mpf) {
        byte[] bytes = null;
        if (mpf != null && !mpf.isEmpty()) {
            try {
                bytes = mpf.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }
}
