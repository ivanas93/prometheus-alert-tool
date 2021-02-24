package com.ivanas.metricgenerator.service;

import com.ivanas.metricgenerator.model.BaseMetric;

public interface MetricsGeneratorService {
    BaseMetric getMetricsGenerated(final String command);
}
