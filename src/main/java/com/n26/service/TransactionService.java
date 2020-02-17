package com.n26.service;

import com.n26.exception.TransactionLimitException;
import com.n26.exception.TransactionFutureException;
import com.n26.model.Transaction;

public interface TransactionService {

    void create(Transaction transaction) throws TransactionLimitException, TransactionFutureException;
    void deleteAll();
}
