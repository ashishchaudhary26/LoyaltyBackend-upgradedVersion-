package com.dto;

public class RewardResponseDto {

    private Long id;
    private Double rewardPercentage;
    private Boolean active;

    public RewardResponseDto(Long id, Double rewardPercentage, Boolean active) {
        this.id = id;
        this.rewardPercentage = rewardPercentage;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public Double getRewardPercentage() {
        return rewardPercentage;
    }

    public Boolean getActive() {
        return active;
    }
}
