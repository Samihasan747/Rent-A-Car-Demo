package com.RentACarDemo.user;

import com.RentACarDemo.cars.customUser.AuthService;
import com.RentACarDemo.cars.dto.CarRequestDto;
import com.RentACarDemo.cars.dto.CarRespondDto;
import com.RentACarDemo.user.dto.UserRequestDto;
import com.RentACarDemo.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    public UserService userService;
    public AuthService authService;
    public UserRepository userRepository;


    public UserController(UserService userService, AuthService authService,UserRepository userRepository){
        this.userService=userService;
        this.authService=authService;
        this.userRepository=userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        UserResponseDto userResp = authService.register(userRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userResp);
    }

    @PostMapping("/book/{cid}")
    public ResponseEntity<String> createBooking(@PathVariable Long cid, Authentication authentication){

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long uid = user.getId();

        userService.createBooking(uid, cid);

        return ResponseEntity.ok("Done");
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelBooking(Authentication authentication){
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long uid = user.getId();
        Long cid = user.getBookedCar().getId();
        userService.cancelBooking(uid, cid);

        return ResponseEntity.ok("Booking Canceled");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id){
        UserResponseDto userResp = userService.getUser(id);

        return ResponseEntity.ok(userResp);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUser(){
        List<UserResponseDto> userList = userService.getAllUser();

        return ResponseEntity.ok(userList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserRequestDto userRequestDto, @PathVariable Long id){
        UserResponseDto carResp = userService.updateCar(userRequestDto, id);

        return ResponseEntity.ok(carResp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id){
        userService.softDelete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
