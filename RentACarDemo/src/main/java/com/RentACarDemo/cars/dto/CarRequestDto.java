package com.RentACarDemo.cars.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CarRequestDto {

    @NotBlank(message = "Name cannot be null")
    String name;

    @NotBlank(message = "Model cannot be null")
    String model;

    @NotNull(message = "Price cannot be null")
    Double price;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
