package com.banking.transaction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long accountNumber;

	@NotNull
	private Long customerId;

	@NotBlank
	private String transactionType;

	@NotNull
	private BigDecimal amount;

	@NotBlank
	private String transactionStatus;

	@NotNull
	private LocalDateTime transactionTime;

	public Transaction updateWith(Transaction transaction) {
		return new Transaction(this.id, transaction.accountNumber, transaction.customerId, transaction.transactionType,
				transaction.amount, transaction.transactionStatus, transaction.transactionTime);
	}
}
