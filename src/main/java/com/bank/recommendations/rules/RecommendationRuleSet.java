package com.bank.recommendations.rules;

import com.bank.recommendations.dto.ProductDto;
import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<ProductDto> checkRequirement(UUID userId);
}