package com.dto;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "ProductReviewDto",
    description = "Represents a product review created by a customer"
)
public class ProductReviewDto {

    @Schema(
        description = "Primary key of the review",
        example = "15",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
        description = "Identifier of the user who wrote the review",
        example = "42"
    )
    private Long userId;

    @Schema(
        description = "Rating given by the user (1 to 5 stars)",
        example = "5"
    )
    private Integer rating;

    @Schema(
        description = "Short title for the review",
        example = "Excellent product!"
    )
    private String reviewTitle;

    @Schema(
        description = "Detailed review description",
        example = "The battery life is amazing and the camera is outstanding."
    )
    private String reviewText;

    @Schema(
        description = "Timestamp when the review was created",
        example = "2025-11-25T12:34:56",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime createdAt;

    public ProductReviewDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getReviewTitle() { return reviewTitle; }
    public void setReviewTitle(String reviewTitle) { this.reviewTitle = reviewTitle; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
