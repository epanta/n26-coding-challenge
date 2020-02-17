package com.n26.factory;

import com.n26.model.Statistic;

public class StatisticFactory {

    public Statistic getDefault() {
        return Statistic.builder()
                .count(1L)
                .sum("88.41")
                .max("88.41")
                .min("88.41")
                .avg("88.41").build();
    }

    public Statistic getEmpty() {
        return Statistic.builder()
                .count(0L)
                .sum("0.00")
                .max("0.00")
                .min("0.00")
                .avg("0.00").build();
    }
}
