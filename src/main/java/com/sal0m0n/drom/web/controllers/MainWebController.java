package com.sal0m0n.drom.web.controllers;

import com.sal0m0n.drom.repository.user.MyUser;
import com.sal0m0n.drom.web.dto.UserDTO;
import com.sal0m0n.drom.web.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainWebController {

    @Autowired
    private DBService dbService;

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

}
