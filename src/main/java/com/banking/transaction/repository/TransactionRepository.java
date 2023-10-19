package com.banking.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banking.transaction.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
