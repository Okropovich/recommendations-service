package com.bank.recommendations.rules;

import com.bank.recommendations.dto.ProductDto;
import com.bank.recommendations.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class Invest500RuleSet implements RecommendationRuleSet {

    private final RecommendationsRepository repository;
    private final ProductDto product;

    public Invest500RuleSet(RecommendationsRepository repository) {
        this.repository = repository;
        this.product = new ProductDto(
                UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),
                "Invest 500",
                "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!"
        );
    }

    @Override
    public Optional<ProductDto> checkRequirement(UUID userId) {
        boolean hasDebit = repository.hasProductType(userId, "DEBIT");
        boolean hasNoInvest = !repository.hasProductType(userId, "INVEST");
        long savingDeposits = repository.getSumOfTransactions(userId, "SAVING", "DEPOSIT");

        if (hasDebit && hasNoInvest && savingDeposits > 1000) {
            return Optional.of(product);
        }
        return Optional.empty();
    }
}