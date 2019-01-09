package com.robot.humanrobot.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.spring.autoconfigure.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Ludovic on 09/01/2019.
 */
@Configuration
public class MetricConfiguration {
    /**
     * Register common tags application instead of job.
     * This application tag is needed for Grafana dashboard.
     *
     * @return registry with registered tags.
     */
    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> {
            registry.config().commonTags("application", "Consumer-with-class");
        };
    }
}
