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
        Long id = user.getId();

        userService.createBooking(id, cid);

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

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getUser(Authentication authentication){
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long uid = user.getId();

        UserResponseDto userResp = userService.getUser(uid);

        return ResponseEntity.ok(userResp);
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserRequestDto userRequestDto, Authentication authentication){
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long uid = user.getId();

        UserResponseDto carResp = userService.updateCar(userRequestDto, uid);
        return ResponseEntity.ok(carResp);
    }


    @PatchMapping("/me")
    public ResponseEntity<String> softDelete(Authentication authentication){
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Long uid = user.getId();

        userService.softDelete(uid);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

        @GetMapping("/debug")
    public Object debug(Authentication auth) {
        return auth.getAuthorities();
    }

}
