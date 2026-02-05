package com.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "product_reviews")
@Schema(name = "ProductReview", description = "Represents a customer's review for a product")
public class Product_Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique ID of the review", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @Schema(description = "Product associated with this review")
    public Products product;

    @Column(name = "user_id")
    @Schema(description = "ID of the user who wrote the review", example = "101")
    public Long userId;

    @Column(nullable = false)
    @Schema(description = "Rating provided by the user (1–5)", example = "5")
    public Integer rating;

    @Column(name = "review_title", length = 255)
    @Schema(description = "Short title for the review", example = "Amazing Product!")
    public String reviewTitle;

    @Column(name = "review_text", columnDefinition = "TEXT")
    @Schema(description = "Detailed feedback written by the user",
            example = "Great build quality, amazing battery life!")
    public String reviewText;

    @Column(name = "created_at")
    @Schema(description = "Timestamp when the review was submitted",
            example = "2025-11-25T10:20:30")
    public LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, id, product, rating, reviewText, reviewTitle, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product_Reviews other = (Product_Reviews) obj;
        return Objects.equals(createdAt, other.createdAt)
                && Objects.equals(id, other.id)
                && Objects.equals(product, other.product)
                && Objects.equals(rating, other.rating)
                && Objects.equals(reviewText, other.reviewText)
                && Objects.equals(reviewTitle, other.reviewTitle)
                && Objects.equals(userId, other.userId);
    }

    @Override
    public String toString() {
        return "Product_Reviews [id=" + id + ", product=" + product + ", userId=" + userId
                + ", rating=" + rating + ", reviewTitle=" + reviewTitle
                + ", reviewText=" + reviewText + ", createdAt=" + createdAt + "]";
    }
}
