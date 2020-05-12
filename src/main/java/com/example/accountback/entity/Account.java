package com.example.accountback.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    private Long no;
    private String owner;
    private Long currentAmount;

    public Account(String owner, Long currentAmount) {
        this.owner = owner;
        this.currentAmount = currentAmount;
    }

    @OneToMany(mappedBy = "debit")
    @JsonManagedReference
    private List<Transaction> debitTransactions;
    @OneToMany(mappedBy = "credit")
    @JsonManagedReference
    private List<Transaction> creditTransactions;

    public void plus(Long amount){
        currentAmount += amount;
    }

    public void minus(Long amount){
        currentAmount -= amount;
    }
}
