package com.n26.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Transaction {

    public static final int TRANSACTION_LIMIT = 60000;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Instant timestamp;
}
