package com.currencyaccount.account.service;

import com.currencyaccount.account.model.Account;
import com.currencyaccount.account.model.requestbody.CreateAccountRequestBody;
import com.currencyaccount.account.model.responsebody.AccountInfoResponseBody;
import com.currencyaccount.currency.model.Currency;
import com.currencyaccount.currency.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.AccessDeniedException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final CurrencyService currencyService;
    private Account account;
    private String uuid;

    public void validateToken(@Nullable String token) throws AccessDeniedException {
        if (token == null || !token.equals(uuid)) {
            throw new AccessDeniedException("You have no permissions.");
        }
    }

    public String createAccount(CreateAccountRequestBody requestBody) {
        if (account == null) {
            account = Account
                    .builder()
                    .firstName(requestBody.getFirstName())
                    .lastName(requestBody.getLastName())
                    .build();
            account.setBalanceItem("pln", new BigDecimal(requestBody.getBalance()));
            uuid = UUID.randomUUID().toString();
        }
        return uuid;
    }

    public AccountInfoResponseBody getAccountInfo() {
        return new AccountInfoResponseBody(account);
    }

    public void exchange(String currencyFrom, String currencyTo, BigDecimal amountFrom,
            BigDecimal amountTo) throws RuntimeException, InterruptedException {

        handleAmountDefinitionException(amountTo, amountFrom);

        Currency currency = currencyService.getCurrency(currencyFrom.equals("pln") ? currencyTo : currencyFrom);

        handleMoneyException(currencyFrom, amountTo, amountFrom, currency);

        setCurrencies(currencyFrom, currencyTo, amountFrom, amountTo, currency);
    }

    private void setCurrencies(String currencyFrom, String currencyTo, BigDecimal amountFrom, BigDecimal amountTo,
            Currency currency) {
        if (amountFrom != null) {
            account.setBalanceItem(currencyFrom, account.getBalance().get(currencyFrom).subtract(amountFrom));
            account.setBalanceItem(currencyTo, getCurrentAmount(currencyTo)
                    .add(amountFrom.multiply(getCourse(currency, currencyFrom))));
        }
        else if (amountTo != null) {
            account.setBalanceItem(currencyFrom,
                                   account.getBalance()
                                          .get(currencyFrom)
                                          .subtract(amountTo.multiply(currency.getRates().get(0).getAsk())));
            account.setBalanceItem(currencyTo, getCurrentAmount(currencyTo).add(amountTo));
        }
    }

    private void handleAmountDefinitionException(BigDecimal amountTo, BigDecimal amountFrom) throws RuntimeException {
        if (amountTo == null && amountFrom == null) {
            throw new RuntimeException("You have to specify amount.");
        }
    }

    private void handleMoneyException(String currencyFrom, BigDecimal amountTo, BigDecimal amountFrom,
            Currency currency) throws RuntimeException {
        if (account.getBalance().get(currencyFrom) == null
            || ((amountTo != null
                 && account.getBalance().get(currencyFrom).multiply(currency.getRates().get(0).getAsk()).compareTo(
                amountTo) < 0)
                || (amountFrom != null && account.getBalance().get(currencyFrom).compareTo(amountFrom) < 0))) {
            throw new RuntimeException("You have too small amount of that currency to execute that operation.");
        }
    }

    private BigDecimal getCurrentAmount(String currency) {
        return account.getBalance().get(currency) != null ? account.getBalance().get(currency) : new BigDecimal(0);
    }

    private BigDecimal getCourse(Currency currency, String currencyFrom) {
        return currency.getCode().toLowerCase().equals(currencyFrom)
               ? currency.getRates().get(0).getBid()
               : BigDecimal.ONE.divide(currency.getRates().get(0).getAsk(), 12, RoundingMode.DOWN);
    }
}
