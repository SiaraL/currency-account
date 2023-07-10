package com.currencyaccount.account.model.responsebody;

import com.currencyaccount.account.model.Account;
import lombok.Data;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Data
public class AccountInfoResponseBody {

    public AccountInfoResponseBody(Account account) {
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        setBalance(account.getBalance());
    }

    private String firstName;
    private String lastName;
    private final Map<String, String> balance = new HashMap<>();

    public void setBalance(Map<String, BigDecimal> balance) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        for (Map.Entry<String, BigDecimal> entry : balance.entrySet()) {
            this.balance.put(entry.getKey(), decimalFormat.format(new BigDecimal(entry.getValue().doubleValue())));
        }
    }
}
