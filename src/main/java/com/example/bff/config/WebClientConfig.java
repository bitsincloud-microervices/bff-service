package com.example.bff.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${cliente.service-url}")
    private String clienteServiceUrl;

    @Bean
    public WebClient clienteWebClient() {
        return WebClient.builder()
                .baseUrl(clienteServiceUrl)
                .build();
    }
}
