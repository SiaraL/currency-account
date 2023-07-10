package com.currencyaccount.account.model.requestbody;

import lombok.Data;

@Data
public class CreateAccountRequestBody {
    private String firstName;
    private String lastName;
    private double balance;
}
