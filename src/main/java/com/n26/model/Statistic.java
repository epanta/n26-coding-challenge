package com.n26.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Statistic {

    private String sum;
    private String avg;
    private String max;
    private String min;
    private Long count;
}