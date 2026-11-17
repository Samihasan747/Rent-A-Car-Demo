package com.RentACarDemo.user.dto;

import com.RentACarDemo.cars.Cars;

public class UserResponseDto {
    Long id;
    String name;
    String email;
    private Cars bookedCar;

    public Cars getBookedCar() {
        return bookedCar;
    }

    public void setBookedCar(Cars bookedCar) {
        this.bookedCar = bookedCar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
}
