package com.banking.transaction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Class representing a transaction in banking application.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

	@ApiModelProperty(notes = "Unique identifier of the Transaction.", example = "1")
	private Long id;

	@ApiModelProperty(notes = "Account Number.", example = "100023456", required = true)
	@NotNull
	private Long accountNumber;

	@ApiModelProperty(notes = "Unique identifier of the Customer.", example = "3124567", required = true)
	@NotNull
	private Long customerId;

	@ApiModelProperty(notes = "Type of Transaction.", example = "Debit", required = true)
	@NotBlank
	private String transactionType;

	@ApiModelProperty(notes = "Transaction Amount.", example = "2000.00", required = true)
	@NotNull
	private BigDecimal amount;

	@ApiModelProperty(notes = "Status of the transaction.", example = "Success", required = true)
	@NotBlank
	private String transactionStatus;

	@ApiModelProperty(notes = "Transaction time.", required = true)
	@NotNull
	private LocalDateTime transactionTime;

}
