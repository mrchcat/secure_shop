package com.github.mrchcat.intershop.config;

import client.PaymentConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PaymentConfig.class)
public class ModuleConfig {
}
