package com.n26.service;

import com.n26.exception.TransactionLimitException;
import com.n26.exception.TransactionFutureException;
import com.n26.factory.StatisticFactory;
import com.n26.factory.TransactionFactory;
import com.n26.model.Statistic;
import com.n26.model.Transaction;
import com.n26.service.impl.StatisticServiceImpl;
import com.n26.service.impl.TransactionServiceImpl;
import com.n26.utils.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.Instant;

public class TransactionServiceTest {

    private TransactionService transactionService;

    private StatisticService statisticService;

    private TransactionFactory transactionFactory;

    private StatisticFactory statisticFactory;

    private Utils utils;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
        utils = new Utils();
        statisticService = new StatisticServiceImpl();
        transactionService = new TransactionServiceImpl(statisticService);
        transactionFactory = new TransactionFactory();
        statisticFactory = new StatisticFactory();
    }

    @Test
    public void shouldCreateTransaction() throws TransactionFutureException, TransactionLimitException {

        transactionService.create(transactionFactory.getDefault());

        Statistic statistic = statisticFactory.getDefault();

        Assert.assertEquals(statisticService.getStatistics().toString(), statistic.toString());
    }

    @Test
    public void shouldDeleteAllTransactions() throws TransactionFutureException, TransactionLimitException {

        transactionService.create(transactionFactory.getDefault());

        transactionService.deleteAll();

        Statistic statistic = statisticFactory.getEmpty();

        Assert.assertEquals(statisticService.getStatistics().toString(), statistic.toString());
    }

    @Test
    public void shouldReturnTrueWhenTimeisErlierThanTransactionLimit() {
        Transaction transaction = new Transaction();
        transaction.setTimestamp(Instant.now());
        Assert.assertFalse(utils.isAfterTransactionLimit(transaction));
    }

    @Test
    public void shouldThrowsExceptionWhenTimeStampFieldisNull() {

        exceptionRule.expect(NullPointerException.class);

        Transaction transaction = new Transaction();
        utils.isTransactionAfterNow(transaction);
    }

}
