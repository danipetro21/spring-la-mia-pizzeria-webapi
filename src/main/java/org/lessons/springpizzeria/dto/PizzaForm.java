package org.lessons.springpizzeria.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.lessons.springpizzeria.model.Ingredient;
import org.lessons.springpizzeria.model.SpecialOffer;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class PizzaForm {


    private Integer id;

    @NotBlank(message = "Name must not be null or blank")
    private String name;


    private String descr;

    @Min(0)
    private float price;

    private MultipartFile photoFile;

    private List<Ingredient> ingredients = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public MultipartFile getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(MultipartFile photoFile) {
        this.photoFile = photoFile;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
