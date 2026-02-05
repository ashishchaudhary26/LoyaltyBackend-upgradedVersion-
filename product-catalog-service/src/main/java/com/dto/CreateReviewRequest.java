package com.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "CreateReviewRequest",
    description = "Payload for creating a product review by a customer"
)
public class CreateReviewRequest {

    
    @Schema(
        description = "ID of the user submitting the review. (In production, retrieved from JWT instead of request body)",
        example = "42",
        required = true
    )
    private Long userId;

    @NotNull
    @Min(1) 
    @Max(5)
    @Schema(
        description = "Rating given to the product (1 to 5)",
        example = "5",
        required = true
    )
    private Integer rating;

    @Schema(
        description = "Title or summary of the review",
        example = "Excellent product!"
    )
    private String reviewTitle;

    @Schema(
        description = "Detailed review text written by the user",
        example = "The phone works flawlessly and battery life is amazing."
    )
    private String reviewText;

    public CreateReviewRequest() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getReviewTitle() { return reviewTitle; }
    public void setReviewTitle(String reviewTitle) { this.reviewTitle = reviewTitle; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
    @Override
    public String toString() {
        return "CreateReviewRequest{" +
                "rating=" + rating +
                ", reviewTitle='" + reviewTitle + '\'' +
                ", reviewText='" + reviewText + '\'' +
                '}';
    }
}
