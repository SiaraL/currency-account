package com.currencyaccount.account.controller;

import com.currencyaccount.account.model.requestbody.CreateAccountRequestBody;
import com.currencyaccount.account.model.requestbody.ExchangeCurrencyRequestBody;
import com.currencyaccount.account.model.responsebody.AccountInfoResponseBody;
import com.currencyaccount.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("account")
    public String createAccount(@RequestBody CreateAccountRequestBody requestBody) {
        return accountService.createAccount(requestBody);
    }

    @GetMapping("account")
    public AccountInfoResponseBody getAccount(
            @RequestParam(required = false) String token) throws AccessDeniedException {
        accountService.validateToken(token);
        return accountService.getAccountInfo();
    }

    @PatchMapping("exchange")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void exchange(@RequestBody ExchangeCurrencyRequestBody requestBody,
            @RequestParam(required = false) String token) throws InterruptedException, AccessDeniedException {
        accountService.validateToken(token);
        accountService.exchange(requestBody.getCurrencyFrom(), requestBody.getCurrencyTo(),
                                requestBody.getAmountFrom(), requestBody.getAmountTo());
    }
}
