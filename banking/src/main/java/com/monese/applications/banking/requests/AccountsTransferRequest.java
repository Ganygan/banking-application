package com.monese.applications.banking.requests;

import java.math.BigDecimal;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountsTransferRequest {
	
	private final @Valid Long toAccountNumber;
	
	private final @Valid BigDecimal amountToBeTransferred;
	
	private final @Valid String transactionType;
	
	private final @Valid String transactionReference;
	
	 @JsonCreator
	  public AccountsTransferRequest(
	      @JsonProperty(value = "toAccountNumber", required = true) Long toAccountNumber,
	      @JsonProperty(value = "amountToBeTransferred", required = true) BigDecimal amountToBeTransferred,
	      @JsonProperty(value = "transactionType", required = true) String transactionType,
	      @JsonProperty(value = "transactionReference", required = true) String transactionReference
	  ) {
	    this.toAccountNumber = toAccountNumber;
	    this.amountToBeTransferred = amountToBeTransferred;
	    this.transactionType = transactionType;
	    this.transactionReference = transactionReference;
	  }

	/**
	 * @return the toAccountNumber
	 */
	public Long getToAccountNumber() {
		return toAccountNumber;
	}

	/**
	 * @return the amountToBeTransferred
	 */
	public BigDecimal getAmountToBeTransferred() {
		return amountToBeTransferred;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @return the transactionReference
	 */
	public String getTransactionReference() {
		return transactionReference;
	}

}
