package com.sal0m0n.drom.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CarDTO {

    private String model;

    private String description;

    private String state;

    private String color;

    private int mileage;

    private Long ownerId;

    private int price;

    private MultipartFile image;

}
