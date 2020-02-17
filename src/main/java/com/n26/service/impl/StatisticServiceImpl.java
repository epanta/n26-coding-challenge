package com.n26.service.impl;

import com.n26.model.Statistic;
import com.n26.model.Transaction;
import com.n26.service.StatisticService;
import com.n26.utils.Utils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

@Service
public class StatisticServiceImpl implements StatisticService {

    private static final int CAPACITY = 1000;

    private final Utils utils = new Utils();

    private final PriorityBlockingQueue<Transaction> transactions =
            new PriorityBlockingQueue<>(CAPACITY, Comparator.comparing(Transaction::getTimestamp));

    private Statistic statistics = calculateStatistics();

    @Override
    public Statistic getStatistics() {
        removeOldTransactions();
        return calculateStatistics();
    }

    private Statistic calculateStatistics() {

        return Statistic.builder()
                .sum(BigDecimal.valueOf(transactions.stream()
                .mapToDouble(transaction -> transaction.getAmount().doubleValue()).sum())
                .setScale(2, RoundingMode.HALF_UP).toString())

                .max(BigDecimal.valueOf(transactions.stream()
                .mapToDouble(transaction -> transaction.getAmount().doubleValue())
                .max().orElse(0.0))
                .setScale(2, RoundingMode.HALF_UP).toString())

                .avg(BigDecimal.valueOf(transactions.stream()
                .mapToDouble(transaction -> transaction.getAmount().doubleValue())
                .average().orElse(0.0))
                .setScale(2, RoundingMode.HALF_UP).toString())

                .min(BigDecimal.valueOf(transactions.stream()
                .mapToDouble(transaction -> transaction.getAmount().doubleValue())
                .min().orElse(0.0))
                .setScale(2, RoundingMode.HALF_UP).toString())

                .count(transactions.stream().count()).build();
    }

    @Scheduled(fixedRate = 1)
    private void removeOldTransactions() {
        while (!transactions.isEmpty() && utils.isAfterTransactionLimit(transactions.peek())) {
                transactions.poll();
        }
        calculateStatistics();
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public void delete() {
        transactions.clear();
    }

}