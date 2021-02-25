package com.ivanas.metricgenerator.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        MetricsGeneratorServiceImpl.class,
})
class MetricsGeneratorServiceTest {

    @Autowired
    MetricsGeneratorService metricsGeneratorService;

    @Test
    void shouldBeIncrementedAndDecrementAndRestart() {
        //Give - When
        var baseMetricsOne = metricsGeneratorService.getMetricsGenerated("INC");
        var baseMetricsTwo = metricsGeneratorService.getMetricsGenerated("DEC");
        var baseMetricsThree = metricsGeneratorService.getMetricsGenerated("INC");
        var baseMetricsRestart = metricsGeneratorService.getMetricsGenerated("REST");

        //Then
        Assertions.assertThat(baseMetricsOne.getValueMetric()).isEqualTo(1);
        Assertions.assertThat(baseMetricsTwo.getValueMetric()).isEqualTo(0);
        Assertions.assertThat(baseMetricsThree.getValueMetric()).isEqualTo(1);
        Assertions.assertThat(baseMetricsRestart.getValueMetric()).isEqualTo(0);

    }

}
