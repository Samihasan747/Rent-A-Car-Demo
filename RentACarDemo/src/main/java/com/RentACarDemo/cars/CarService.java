package com.RentACarDemo.cars;

import com.RentACarDemo.cars.dto.CarRequestDto;
import com.RentACarDemo.cars.dto.CarRespondDto;
import com.RentACarDemo.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    public CarRepository carRepository;

    public CarService(CarRepository carRepository){
        this.carRepository=carRepository;
    }

    public CarRespondDto  createCar(CarRequestDto carRequestDto){

        Cars car = mapToEntity(carRequestDto);

        carRepository.save(car);

        return mapToDto(car);
    }

    public CarRespondDto getCar(Long id){
        Cars car = carRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Car with id "+id+" does not exist!")) ;

        CarRespondDto carResp = mapToDto(car);

        return carResp;
    }

    public List<CarRespondDto> getAllCar(){

        List<Cars>  carList = carRepository.findAll();

        return carList.stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<CarRespondDto> getAllAvailableCar(){

        List<Cars>  carList = carRepository.findByIsBookedFalse();

        return carList.stream()
                .map(this::mapToDto)
                .toList();
    }


    public CarRespondDto updateCar(CarRequestDto carReq, Long id){

        Cars car = carRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Car with id "+id+" does not exist"));

        Cars updatedCar = mapToUpdateCar(car, carReq);

        carRepository.save(car);

        CarRespondDto carResp = mapToDto(car);
        return carResp;
    }

    public void deleteCar(Long id){
        Cars car = carRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Car with id "+id+" does not exist"));

        carRepository.delete(car);
    }

    //DTO Mapping

    private Cars mapToUpdateCar(Cars car, CarRequestDto carReq){

        car.setName(carReq.getName());
        car.setModel(carReq.getModel());
        car.setPrice(carReq.getPrice());

        return car;
    }

    private Cars mapToEntity(CarRequestDto carRequestDto){
        Cars car = new Cars();

        car.setName(carRequestDto.getName());
        car.setModel(carRequestDto.getModel());
        car.setBooked(false);
        car.setPrice(carRequestDto.getPrice());

        return car;
    }

    private CarRespondDto mapToDto(Cars car){
        CarRespondDto carRespondDto = new CarRespondDto ();

        carRespondDto.setName(car.getName());
        carRespondDto.setModel(car.getModel());
        carRespondDto.setPrice(car.getPrice());
        carRespondDto.setBooked(car.getBooked());
        carRespondDto.setId(car.getId());

        return carRespondDto;
    }

}
