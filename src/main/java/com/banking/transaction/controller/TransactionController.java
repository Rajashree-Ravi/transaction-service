package com.banking.transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.transaction.model.TransactionDto;
import com.banking.transaction.exception.BankingException;
import com.banking.transaction.messaging.TopicProducer;
import com.banking.transaction.service.TransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(produces = "application/json", value = "Operations pertaining to manage transactions in banking application")
@RequestMapping("/api/transaction")
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	private TopicProducer topicProducer;

	@Autowired
	public TransactionController(TopicProducer topicProducer) {
		this.topicProducer = topicProducer;
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieve specific transaction with the specified transaction id", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved transaction with the transaction id"),
			@ApiResponse(code = 404, message = "Transaction with specified transaction id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<TransactionDto> getTransactionById(@PathVariable("id") long id) {

		TransactionDto transaction = transactionService.getTransactionById(id);
		if (transaction != null)
			return new ResponseEntity<>(transaction, HttpStatus.OK);
		else
			throw new BankingException("transaction-not-found", String.format("Transaction with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@PostMapping("/initiateTransaction")
	@ApiOperation(value = "Create a new transaction", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created a transaction"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transaction) {
		TransactionDto savedTransaction = transactionService.initiateTransaction(transaction);
		topicProducer.send(savedTransaction);
		return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
	}

	@PutMapping("/completeTransaction/{id}")
	@ApiOperation(value = "Complete a transaction", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully completed transaction"),
			@ApiResponse(code = 404, message = "Transaction with specified transaction id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<TransactionDto> debitTransaction(@PathVariable("id") long id,
			@RequestBody TransactionDto transaction) {

		TransactionDto updatedTransaction = transactionService.completeTransaction(id, transaction);
		if (updatedTransaction != null) {
			topicProducer.send(updatedTransaction);
			return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
		} else
			throw new BankingException("transaction-not-found", String.format("Transaction with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}
}
