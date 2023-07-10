package com.currencyaccount.account.model.requestbody;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeCurrencyRequestBody {
    private String currencyFrom;
    private String currencyTo;
    @Nullable
    private BigDecimal amountFrom;
    @Nullable
    private BigDecimal amountTo;
}
