package com.example.accountback.rest;

import com.example.accountback.AccountNotFound;
import com.example.accountback.NegativeValue;
import com.example.accountback.entity.Account;
import com.example.accountback.entity.Transaction;
import com.example.accountback.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    Service service;

    @PostMapping(path = "/account")
    ResponseEntity<Account> createAccount(@RequestParam @NotBlank String name, @RequestParam @NotBlank Long amount) throws NegativeValue {
        return ResponseEntity.status(201).body(service.createAccount(name, amount));
    }

    @PostMapping(path = "/transaction")
    ResponseEntity<Transaction> makeTransaction(@RequestParam @NotBlank Long debitAccNo, @RequestParam @NotBlank Long creditAccNo,
                                @RequestParam @NotBlank Long amount) throws AccountNotFound, NegativeValue {
        return ResponseEntity.status(201).body(service.makeTransaction(debitAccNo, creditAccNo, amount));
    }

    @GetMapping(path = "/account/{accountNo}")
    Account getAccount(@PathVariable @NotBlank Long accountNo) throws AccountNotFound {
        return service.getAccount(accountNo);
    }

    @GetMapping(path = "/accounts")
    Collection<Account>getAllAccounts(){
        return service.getAllAccounts();
    }

    @GetMapping(path = "/transactions")
    Collection<Transaction>getAllTransactions(){
        return service.getAllTransactions();
    }

}
