package com.RentACarDemo.cars;

import jakarta.persistence.*;

@Entity
@Table(name = "cars_table")
public class Cars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="car_id")
    private Long id;
    private String name;
    private String model;
    private Boolean isBooked;
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getBooked() {
        return isBooked;
    }

    public void setBooked(Boolean booked) {
        isBooked = booked;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
