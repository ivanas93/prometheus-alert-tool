package com.ivanas.metricgenerator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class BaseMetric {
    private Integer valueMetric;

    public abstract Integer calculateMetric();
}
