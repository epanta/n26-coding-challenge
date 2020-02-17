package com.n26.factory;

import com.n26.model.Transaction;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionFactory {

    public Transaction getDefault() {
        return Transaction.builder()
                .amount(BigDecimal.valueOf(88.41))
                .timestamp(Instant.now()).build();
    }

    public Transaction getEmpty() {
        return Transaction.builder()
                .amount(BigDecimal.valueOf(0.00))
                .timestamp(Instant.now().plusSeconds(1111)).build();
    }
}
