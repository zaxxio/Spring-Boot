package com.avaand.app.integration;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@Log
@Configuration
@EnableIntegration
@IntegrationComponentScan
public class IntegrationConfig {
}
