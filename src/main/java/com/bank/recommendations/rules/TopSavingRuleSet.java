package com.bank.recommendations.rules;

import com.bank.recommendations.dto.ProductDto;
import com.bank.recommendations.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TopSavingRuleSet implements RecommendationRuleSet {

    private final RecommendationsRepository repository;
    private final ProductDto product;

    public TopSavingRuleSet(RecommendationsRepository repository) {
        this.repository = repository;
        this.product = new ProductDto(
                UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"),
                "Top Saving",
                "Откройте свою собственную «Копилку» с нашим банком! «Копилку» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!"
        );
    }

    @Override
    public Optional<ProductDto> checkRequirement(UUID userId) {
        boolean hasDebit = repository.hasProductType(userId, "DEBIT");

        long debitDeposits = repository.getSumOfTransactions(userId, "DEBIT", "DEPOSIT");
        long savingDeposits = repository.getSumOfTransactions(userId, "SAVING", "DEPOSIT");
        boolean depositCondition = (debitDeposits >= 50000) || (savingDeposits >= 50000);

        long debitWithdraws = repository.getSumOfTransactions(userId, "DEBIT", "WITHDRAW");
        boolean moreDepositsThanWithdraws = debitDeposits > debitWithdraws;

        if (hasDebit && depositCondition && moreDepositsThanWithdraws) {
            return Optional.of(product);
        }
        return Optional.empty();
    }
}