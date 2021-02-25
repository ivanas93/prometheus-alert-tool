package com.ivanas.metricgenerator.model;

public class LowMetric extends BaseMetric {
    public LowMetric(final Integer valueMetric) {
        super(valueMetric);
    }

    @Override
    public Integer calculateMetric() {
        this.setValueMetric(getValueMetric() - 1);
        return getValueMetric();
    }
}
