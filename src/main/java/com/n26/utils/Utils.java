package com.n26.utils;

import com.n26.model.Transaction;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class Utils {

    public int TRANSACTION_LIMIT = 60000;

    public boolean isAfterTransactionLimit(Transaction transaction) {
        return Instant.now().toEpochMilli() - transaction.getTimestamp().toEpochMilli() > TRANSACTION_LIMIT;
    }

    public boolean isTransactionAfterNow(Transaction transaction) {
        return transaction.getTimestamp().isAfter(Instant.now());
    }
}
