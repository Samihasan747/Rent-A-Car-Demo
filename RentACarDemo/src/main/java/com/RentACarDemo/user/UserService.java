package com.RentACarDemo.user;

import com.RentACarDemo.cars.CarRepository;
import com.RentACarDemo.cars.Cars;
import com.RentACarDemo.cars.customUser.AuthService;
import com.RentACarDemo.exceptions.ResourceNotFoundException;
import com.RentACarDemo.user.dto.UserRequestDto;
import com.RentACarDemo.user.dto.UserResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public UserRepository userRepository;
    public CarRepository carRepository;

    public UserService(UserRepository userRepository, CarRepository carRepository){
        this.userRepository=userRepository;
        this.carRepository=carRepository;
    }

    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto){

        User user = mapToUser(userRequestDto);

        userRepository.save(user);

        UserResponseDto userResp = mapToDto(user);

        return userResp;
    }

    @Transactional
    public void createBooking(Long uid, Long cid){

        User user = userRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getBookedCar() != null) {
            throw new RuntimeException("User already has a booking");
        }

        Cars car = carRepository.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Car does not exist"));

        if (car.getBooked() != false) {
            throw new RuntimeException("Car is not free");
        }


        user.setBookedCar(car);
        car.setBooked(true);

        userRepository.save(user);
        carRepository.save(car);
    }

    @Transactional
    public void cancelBooking(Long uid, Long cid){

        User user = userRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cars car = carRepository.findById(cid)
                .orElseThrow(() -> new ResourceNotFoundException("Car does not exist"));

        if(user.getBookedCar() != car){
            throw new ResourceNotFoundException("Invalid Request");
        }

        user.setBookedCar(null);
        car.setBooked(false);

        userRepository.save(user);
        carRepository.save(car);
    }

    @Transactional
    public UserResponseDto getUser(Long id){
        User user = userRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(()-> new ResourceNotFoundException("User with id "+id+" does not exist"));

        UserResponseDto userResp = mapToDto(user);
        return userResp;
    }

    @Transactional
    public List<UserResponseDto> getAllUser(){

        List<User> userList = userRepository.findByIsDeletedFalse();

        return userList.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional
    public UserResponseDto updateCar(UserRequestDto userReq, Long id){

        User user = userRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(()-> new ResourceNotFoundException("User with id "+id+" does not exist"));

        User updatedUser = mapToUpdateUser(user, userReq);

        userRepository.save(user);

        UserResponseDto userResp = mapToDto(user);
        return userResp;
    }

    @Transactional
    public void deleteUser(Long id){
        User user = userRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User with id "+id+" does not exist"));

        userRepository.delete(user);
    }

    @Transactional
    public void softDelete(Long id){
        User user = userRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(()-> new ResourceNotFoundException("User with id "+id+" does not exist"));

        user.setDeleted(true);

        userRepository.save(user);
    }

    //DTO Mapping

    private User mapToUpdateUser(User user, UserRequestDto userReq){

        user.setName(userReq.getName());
        user.setEmail(userReq.getEmail());

        return user;
    }

    private User mapToUser(UserRequestDto userRequestDto){
        User user = new User();

        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setRole(Role.CUSTOMER);
        user.setDeleted(false);

        return user;
    }

    private UserResponseDto mapToDto(User user){
        UserResponseDto userResp = new UserResponseDto();

        userResp.setId(user.getId());
        userResp.setName(user.getName());
        userResp.setEmail(user.getEmail());
        userResp.setBookedCar(user.getBookedCar());

        return userResp;
    }

}
