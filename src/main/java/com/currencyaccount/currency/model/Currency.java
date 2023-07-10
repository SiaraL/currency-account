package com.currencyaccount.currency.model;

import lombok.Data;
import java.util.List;

@Data
public class Currency {
    private String table;
    private String currency;
    private String code;
    private List<Rate> rates;
}
