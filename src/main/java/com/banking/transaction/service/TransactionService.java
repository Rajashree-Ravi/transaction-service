package com.banking.transaction.service;

import com.banking.transaction.model.TransactionDto;

public interface TransactionService {

	TransactionDto getTransactionById(long id);

	TransactionDto initiateTransaction(TransactionDto transactionDto);

	TransactionDto completeTransaction(long id, TransactionDto transactionDto);
}
