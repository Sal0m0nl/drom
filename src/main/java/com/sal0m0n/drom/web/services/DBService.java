package com.sal0m0n.drom.web.services;

import com.sal0m0n.drom.repository.CarsRepository;
import com.sal0m0n.drom.repository.UsersRepository;
import com.sal0m0n.drom.repository.car.Car;
import com.sal0m0n.drom.repository.user.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DBService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private UsersRepository usersRepository;

    public List<Car> getFiveCars() {

        return carsRepository.findAll();

    }

    public Optional<Car> findCarById(Long id) {

        return carsRepository.findById(id);

    }

    public void saveUser(MyUser user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        usersRepository.save(user);

    }

    public void saveCar(Car car) {

        carsRepository.save(car);

    }

    public Optional<MyUser> findUserByUsername(String username) {

        return usersRepository.findByUsername(username);

    }

}
