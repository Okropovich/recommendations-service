package com.bank.recommendations.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class RecommendationsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean hasProductType(UUID userId, String productType) {
        String sql = "SELECT COUNT(*) FROM transactions t " +
                     "JOIN products p ON t.product_id = p.id " +
                     "WHERE t.user_id = ? AND p.type = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, productType);
        return count != null && count > 0;
    }

    public long getSumOfTransactions(UUID userId, String productType, String transactionType) {
        String sql = "SELECT SUM(t.amount) FROM transactions t " +
                     "JOIN products p ON t.product_id = p.id " +
                     "WHERE t.user_id = ? AND p.type = ? AND t.type = ?";
        Long sum = jdbcTemplate.queryForObject(sql, Long.class, userId, productType, transactionType);
        return sum != null ? sum : 0L;
    }
}