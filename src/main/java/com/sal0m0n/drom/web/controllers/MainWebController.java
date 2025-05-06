package com.sal0m0n.drom.web.controllers;

import com.sal0m0n.drom.repository.car.Car;
import com.sal0m0n.drom.repository.user.MyUser;
import com.sal0m0n.drom.web.dto.CarDTO;
import com.sal0m0n.drom.web.dto.UserDTO;
import com.sal0m0n.drom.web.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class MainWebController {

    @Autowired
    private DBService dbService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String mainPage(Model model) {

        model.addAttribute("cars", dbService.getFiveCars());

        return "main";
    }

    @GetMapping("/cars/{id}")
    public String carPage(@PathVariable(value = "id") Long id, Model model) {

        model.addAttribute("car", dbService.findCarById(id).orElseThrow());

        return "car";

    }

    @GetMapping("add-car")
    public String addCar() {

        return "add-car";

    }

    @PostMapping("/register-user")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserDTO userDTO) {

        MyUser user = new MyUser();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRoles("USER");

        dbService.saveUser(user);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", "User registered successfully"));

    }

    @PostMapping("/register-car")
    public ResponseEntity<Map<String, String>> registerCar(@AuthenticationPrincipal UserDetails userDetails,
                                                           @ModelAttribute CarDTO carDTO) throws IOException {

        Car car = new Car();

        if(carDTO.getImage() != null) {

           File uploadFolder = new File(uploadPath);

           if(!uploadFolder.exists()) {
               uploadFolder.mkdir();
           }

            String uuidFile = UUID.randomUUID().toString();

            String originalFilename = carDTO.getImage().getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String resultFilename = uuidFile + fileExtension;

            carDTO.getImage().transferTo(new File(uploadPath + "/" + resultFilename));

            car.setImageName(resultFilename);
        }

        car.setModel(carDTO.getModel());
        car.setColor(carDTO.getColor());
        car.setMileage(carDTO.getMileage());
        car.setDescription(carDTO.getDescription());
        car.setState(carDTO.getState());
        car.setPrice(carDTO.getPrice());
        car.setOwnerId(dbService.findUserByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found")).getId());

        dbService.saveCar(car);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", "Car registered successfully"));

    }

}
