package com.bank.recommendations.rules;

import com.bank.recommendations.dto.ProductDto;
import com.bank.recommendations.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleCreditRuleSet implements RecommendationRuleSet {

    private final RecommendationsRepository repository;
    private final ProductDto product;

    public SimpleCreditRuleSet(RecommendationsRepository repository) {
        this.repository = repository;
        this.product = new ProductDto(
                UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
                "Простой кредит",
                "Откройте мир выгодных кредитов с нами! Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно!"
        );
    }

    @Override
    public Optional<ProductDto> checkRequirement(UUID userId) {
        boolean hasNoCredit = !repository.hasProductType(userId, "CREDIT");

        long debitDeposits = repository.getSumOfTransactions(userId, "DEBIT", "DEPOSIT");
        long debitWithdraws = repository.getSumOfTransactions(userId, "DEBIT", "WITHDRAW");
        boolean moreDepositsThanWithdraws = debitDeposits > debitWithdraws;

        boolean spendCondition = debitWithdraws > 100000;

        if (hasNoCredit && moreDepositsThanWithdraws && spendCondition) {
            return Optional.of(product);
        }
        return Optional.empty();
    }
}