package com.sal0m0n.drom.repository;

import com.sal0m0n.drom.repository.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarsRepository extends JpaRepository<Car, Long> {



}
