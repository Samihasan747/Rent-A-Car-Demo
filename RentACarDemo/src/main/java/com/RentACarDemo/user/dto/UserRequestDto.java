package com.RentACarDemo.user.dto;

import jakarta.validation.constraints.*;

public class UserRequestDto {

    @NotBlank(message = "Name cannot be null")
    String name;

    @NotBlank(message = "Please Enter your Email")
    @Email(message = "Invalid Email format")
    String email;

    @NotBlank(message = "Please Enter your Password")
    @Size(min = 8, message = "Password must be at least 8 character long")
    String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
