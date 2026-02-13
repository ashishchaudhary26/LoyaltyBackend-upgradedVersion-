package com.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "rewards")
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reward_percentage", nullable = false)
    private Double rewardPercentage;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Products product;

    public Reward() {
    }

    public Reward(Double rewardPercentage, Boolean active, Products product) {
        this.rewardPercentage = rewardPercentage;
        this.active = active;
        this.product = product;
    }

    public Long getId() {
        return id;
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

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }
}
