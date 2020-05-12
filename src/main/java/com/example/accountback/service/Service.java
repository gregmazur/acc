package com.example.accountback.service;

import com.example.accountback.AccountNotFound;
import com.example.accountback.NegativeValue;
import com.example.accountback.entity.Account;
import com.example.accountback.entity.Transaction;

import java.util.Collection;

public interface Service {
    Account createAccount(String ownerName, Long amount) throws NegativeValue;
    Transaction makeTransaction(Long debitAccNo, Long creditAccNo, Long amount) throws AccountNotFound, NegativeValue;
    Account getAccount(Long accNo) throws AccountNotFound;
    Collection<Account> getAllAccounts();
    Collection<Transaction> getAllTransactions();
}
