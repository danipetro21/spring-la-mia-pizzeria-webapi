package org.lessons.springpizzeria.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "pizza")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name must not be null or blank")
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String descr;
    private String photo;
    @Min(0)
    private float price;

    @OneToMany(mappedBy = "pizza", cascade = {CascadeType.REMOVE})
    private List<SpecialOffer> listOffer = new ArrayList<>();


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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<SpecialOffer> getListOffer() {
        return listOffer;
    }

    public void setListOffer(List<SpecialOffer> listOffer) {
        this.listOffer = listOffer;
    }
}
