package com.ivanas.metricgenerator.model;

public class HighMetric extends BaseMetric {

    public HighMetric(final Integer valueMetric) {
        super(valueMetric);
    }

    @Override
    public Integer calculateMetric() {
        return getValueMetric() + 1;
    }
}
