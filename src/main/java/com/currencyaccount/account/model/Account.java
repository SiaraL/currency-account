package com.currencyaccount.account.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class Account {
    private String firstName;
    private String lastName;
    private final Map<String, BigDecimal> balance = new HashMap<>();

    public void setBalanceItem(String key, BigDecimal value) {
        balance.put(key, value);
    }
}
