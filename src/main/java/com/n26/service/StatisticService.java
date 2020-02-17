package com.n26.service;

import com.n26.model.Statistic;
import com.n26.model.Transaction;

public interface StatisticService {
    Statistic getStatistics();
    void addTransaction(Transaction transaction);
    void delete();
}
