package com.n26.service;

import com.n26.factory.StatisticFactory;
import com.n26.service.impl.StatisticServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StatisticServiceTest {


    private StatisticService statisticService;

    private StatisticFactory statisticFactory;

    @Before
    public void setUp() {
        statisticService = new StatisticServiceImpl();
        statisticFactory = new StatisticFactory();
    }

    @Test
    public void shouldReturnEmptyStatistics() {
        Assert.assertEquals(statisticService.getStatistics().toString(), statisticFactory.getEmpty().toString());
    }
}
