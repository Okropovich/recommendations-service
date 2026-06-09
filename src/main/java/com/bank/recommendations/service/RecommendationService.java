package com.bank.recommendations.service;

import com.bank.recommendations.dto.ProductDto;
import com.bank.recommendations.dto.RecommendationResponse;
import com.bank.recommendations.rules.RecommendationRuleSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> ruleSets;

    public RecommendationService(List<RecommendationRuleSet> ruleSets) {
        this.ruleSets = ruleSets;
    }

    public RecommendationResponse getRecommendationsForUser(UUID userId) {
        List<ProductDto> validProducts = new ArrayList<>();

        for (RecommendationRuleSet ruleSet : ruleSets) {
            Optional<ProductDto> result = ruleSet.checkRequirement(userId);
            result.ifPresent(validProducts::add);
        }

        return new RecommendationResponse(userId, validProducts);
    }
}