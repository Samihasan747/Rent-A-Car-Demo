package com.RentACarDemo.cars;

import com.RentACarDemo.cars.dto.CarRequestDto;
import com.RentACarDemo.cars.dto.CarRespondDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    public CarService carService;

    public CarController(CarService carService){
        this.carService=carService;
    }

    @PostMapping
    public ResponseEntity<CarRespondDto> createCar(@Valid @RequestBody CarRequestDto carRequestDto){
        CarRespondDto carResp = carService.createCar(carRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(carResp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarRespondDto> getCar(@PathVariable Long id){
        CarRespondDto carResp = carService.getCar(id);

        return ResponseEntity.ok(carResp);
    }

    @GetMapping
    public ResponseEntity<List<CarRespondDto>> getAllCar(){
        List<CarRespondDto> carList = carService.getAllCar();

        return ResponseEntity.ok(carList);
    }

    @GetMapping("/available")
    public ResponseEntity<List<CarRespondDto>> getAllAvailableCar(){
        List<CarRespondDto> carList = carService.getAllAvailableCar();

        return ResponseEntity.ok(carList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarRespondDto> updateCar(@Valid @RequestBody CarRequestDto carRequestDto, @PathVariable Long id){
        CarRespondDto carResp = carService.updateCar(carRequestDto, id);

        return ResponseEntity.ok(carResp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable Long id){
        carService.deleteCar(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
