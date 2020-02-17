package com.n26.service.impl;

import com.n26.exception.TransactionLimitException;
import com.n26.exception.TransactionFutureException;
import com.n26.model.Transaction;
import com.n26.service.StatisticService;
import com.n26.service.TransactionService;
import com.n26.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final StatisticService statisticService;

    private final Utils utils = new Utils();

    @Override
    public void create(Transaction transaction) throws TransactionLimitException, TransactionFutureException {
        if (utils.isAfterTransactionLimit(transaction)) throw new TransactionLimitException();
        if (utils.isTransactionAfterNow(transaction)) throw new TransactionFutureException();
        statisticService.addTransaction(transaction);
    }

    @Override
    public void deleteAll() {
        statisticService.delete();
    }
}
