package com.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private Double price;
    private Boolean rewardEnabled;
    private Double rewardPercentage;

}