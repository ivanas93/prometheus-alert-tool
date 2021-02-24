package com.ivanas.metricgenerator.model;

public class ZeroMetric extends BaseMetric {
    public ZeroMetric(final Integer valueMetric) {
        super(valueMetric);
    }

    @Override
    public Integer calculateMetric() {
        return 0;
    }
}
