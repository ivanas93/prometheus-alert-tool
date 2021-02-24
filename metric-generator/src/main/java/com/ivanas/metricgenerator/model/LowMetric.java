package com.ivanas.metricgenerator.model;

public class LowMetric extends BaseMetric {
    public LowMetric(final Integer valueMetric) {
        super(valueMetric);
    }

    @Override
    public Integer calculateMetric() {
        return getValueMetric() - 1;
    }
}
