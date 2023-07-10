package com.currencyaccount.currency.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Rate {
    private String no;
    private Date effectiveDate;
    private BigDecimal bid;
    private BigDecimal ask;
}
