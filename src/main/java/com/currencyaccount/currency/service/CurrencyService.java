package com.currencyaccount.currency.service;

import com.currencyaccount.currency.model.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final WebClient currencyUrl;
    @Value("${api.currency.day}")
    private String day;

    private Map<String, Currency> currencies = new HashMap();

    public Currency getCurrency(String currency) throws InterruptedException {
        updateCurrencies(currency);
        return currencies.get(currency);
    }

    private void updateCurrencies(String currency) throws InterruptedException {
        handleApiResponseByThread(
                () -> currencies.put(currency, getCurrencyFromApi(currency + day))
        );
    }

    private Currency getCurrencyFromApi(String currencySpecificUrl) {
        return currencyUrl
                .get()
                .uri(currencySpecificUrl)
                .retrieve()
                .bodyToMono(Currency.class)
                .block(Duration.ofSeconds(3));
    }

    private void handleApiResponseByThread(Runnable operation) throws InterruptedException {
        Thread thread = new Thread(operation);
        thread.start();
        thread.join();
    }
}
