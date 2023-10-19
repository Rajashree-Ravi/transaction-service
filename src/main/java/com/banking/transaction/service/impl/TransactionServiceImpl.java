package com.banking.transaction.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.transaction.entity.Transaction;
import com.banking.transaction.model.TransactionDto;
import com.banking.transaction.repository.TransactionRepository;
import com.banking.transaction.service.impl.TransactionServiceImpl;
import com.banking.transaction.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public TransactionDto getTransactionById(long id) {
		Optional<Transaction> transaction = transactionRepository.findById(id);
		return (transaction.isPresent() ? mapper.map(transaction.get(), TransactionDto.class) : null);
	}

	@Override
	public TransactionDto initiateTransaction(TransactionDto transactionDto) {
		Transaction transaction = mapper.map(transactionDto, Transaction.class);
		return mapper.map(transactionRepository.save(transaction), TransactionDto.class);
	}

	@Override
	public TransactionDto completeTransaction(long id, TransactionDto transactionDto) {
		Optional<Transaction> updatedTransaction = transactionRepository.findById(id).map(existingTransaction -> {
			Transaction transaction = mapper.map(transactionDto, Transaction.class);
			return transactionRepository.save(existingTransaction.updateWith(transaction));
		});

		return (updatedTransaction.isPresent() ? mapper.map(updatedTransaction.get(), TransactionDto.class) : null);
	}

}
