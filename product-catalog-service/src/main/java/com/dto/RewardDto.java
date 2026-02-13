package com.dto;

public class RewardDto {

    private Long productId;
    private Double rewardPercentage;
    private Boolean active;

    public RewardDto() {
    }

    public RewardDto(Long productId, Double rewardPercentage, Boolean active) {
        this.productId = productId;
        this.rewardPercentage = rewardPercentage;
        this.active = active;
    }

    // getters & setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getRewardPercentage() {
        return rewardPercentage;
    }

    public void setRewardPercentage(Double rewardPercentage) {
        this.rewardPercentage = rewardPercentage;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
