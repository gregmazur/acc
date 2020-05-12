package com.example.accountback.service;

import com.example.accountback.AccountNotFound;
import com.example.accountback.NegativeValue;
import com.example.accountback.entity.Account;
import com.example.accountback.entity.Transaction;
import com.example.accountback.repository.AccountRepository;
import com.example.accountback.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Account createAccount(String ownerName, Long amount) throws NegativeValue {
        if (isAmountNegative(amount)){
            throw new NegativeValue();
        }
        Account account = new Account(ownerName, amount);
        account = accountRepository.save(account);
        return account;
    }

    private boolean isAmountNegative(Long amount){
        return Long.signum(amount) != 1;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Transaction makeTransaction(Long debitAccNo, Long creditAccNo, Long amount) throws AccountNotFound, NegativeValue {
        Account debitAccount = accountRepository.findByNoLocked(debitAccNo).orElseThrow(AccountNotFound::new);
        Account creditAccount = accountRepository.findByNoLocked(creditAccNo).orElseThrow(AccountNotFound::new);
        Long availableCredit = creditAccount.getCurrentAmount();
        if (availableCredit - amount < 0) {
            throw new NegativeValue();
        }
        creditAccount.minus(amount);
        debitAccount.plus(amount);
        Transaction transaction = new Transaction(debitAccount, creditAccount, amount);
        transaction = transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccount(Long accNo) throws AccountNotFound {
        return accountRepository.findById(accNo).orElseThrow(AccountNotFound::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
