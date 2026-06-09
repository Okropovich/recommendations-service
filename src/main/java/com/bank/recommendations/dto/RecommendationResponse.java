package com.bank.recommendations.dto;

import java.util.List;
import java.util.UUID;

public class RecommendationResponse {
    private UUID user_id;
    private List<ProductDto> recommendations;

    public RecommendationResponse() {}

    public RecommendationResponse(UUID userId, List<ProductDto> recommendations) {
        this.user_id = userId;
        this.recommendations = recommendations;
    }

    public UUID getUser_id() { return user_id; }
    public void setUser_id(UUID userId) { this.user_id = userId; }

    public List<ProductDto> getRecommendations() { return recommendations; }
    public void setRecommendations(List<ProductDto> recommendations) { this.recommendations = recommendations; }
}