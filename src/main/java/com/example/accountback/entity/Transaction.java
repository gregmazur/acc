package com.example.accountback.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JsonBackReference
    private Account debit;
    @ManyToOne
    @JsonBackReference
    private Account credit;
    private Long amount;
    private Timestamp createdAt;

    public Transaction(Account debit, Account credit, Long amount) {
        this.debit = debit;
        this.credit = credit;
        this.amount = amount;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
