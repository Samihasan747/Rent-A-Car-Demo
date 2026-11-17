package com.RentACarDemo.cars;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Cars , Long> {

    List<Cars> findByIsBookedFalse();

    Optional<Cars> findByIdAndIsBookedFalse(Long id);

    Optional<Object> findByIdAndIsBookedTrue(Long cid);
}
