package com.currencyaccount.currency.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CurrencyConfiguration {

    @Value("${api.currency.url}")
    private String currencyUrl;

    @Bean
    public WebClient currencyUSD() {
        return WebClient.create(currencyUrl);
    }
}
