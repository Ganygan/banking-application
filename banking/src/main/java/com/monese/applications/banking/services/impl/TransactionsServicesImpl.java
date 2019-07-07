/**
 * 
 */
package com.monese.applications.banking.services.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monese.applications.banking.exceptions.ApplicationException;
import com.monese.applications.banking.model.Account;
import com.monese.applications.banking.model.Transactions;
import com.monese.applications.banking.repository.AccountsRepository;
import com.monese.applications.banking.repository.TransactionsRespository;
import com.monese.applications.banking.requests.AccountsTransferRequest;
import com.monese.applications.banking.responses.TransactionsResponse;
import com.monese.applications.banking.services.TransactionsService;

/**
 * @author gnagarajan
 *
 */
@Service
public class TransactionsServicesImpl implements TransactionsService{

	@Autowired
	public TransactionsServicesImpl() {
		
	}
	
	private static final Logger LOG = LogManager.getLogger(TransactionsServicesImpl.class);
	
	@Autowired
	private TransactionsRespository transRepository;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	 @Override
	  public TransactionsResponse listAllTransactions(Long accountNumber) throws ApplicationException {
	    LOG.entry(accountNumber);
	    TransactionsResponse transactionsResponse = new TransactionsResponse();
	    List<Transactions> allTransactions = transRepository.findByTransFromAccNumberOrTransToAccNumber(accountNumber, accountNumber);
	    LOG.debug("Number of transactions returned :"+allTransactions.size());
	    transactionsResponse.setListTransactions(allTransactions);
	    return LOG.traceExit(transactionsResponse);
	  }
	 
	 
	 @Override
	  public void postTransactions(AccountsTransferRequest accountsTransferRequest, Long fromAccountNumber) throws ApplicationException {
		 
		 Transactions transaction = mapTransferRequestToTransaction(accountsTransferRequest, fromAccountNumber);
		 
		 boolean validTransaction = validateAccountNumber(transaction.getTransFromAccNumber()) && 
				 validateAccountNumber(transaction.getTransToAccNumber()) &&
				 ValidateBalanceAfterTransfer(transaction.getTransFromAccNumber(), transaction.getTransAmount());
		 
		 if (validTransaction) {
			 	transRepository.save(transaction);
			 	updateAccountBalances(transaction);
		 }
		 
	 }
	 
	 private Transactions mapTransferRequestToTransaction(AccountsTransferRequest accountsTransferRequest, Long fromAccountNumber) {
		 Transactions transaction = new Transactions();
		 transaction.setTransFromAccNumber(fromAccountNumber);
		 transaction.setTransToAccNumber(accountsTransferRequest.getToAccountNumber());
		 transaction.setTransAmount(accountsTransferRequest.getAmountToBeTransferred());
		 transaction.setTransReference(accountsTransferRequest.getTransactionReference());
		 transaction.setTransStatus("S");
		 transaction.setTransType(accountsTransferRequest.getTransactionType());
		 return transaction;
	 }
	 
	 private boolean validateAccountNumber(Long accNumber) {
		 return accountsRepository.existsByAccountNumber(accNumber);
	 }
	 
	 private boolean ValidateBalanceAfterTransfer(Long accNumber, BigDecimal amount) {
		 Account account =  accountsRepository.findByAccountNumber(accNumber);
		 return account.getAccountBalance().subtract(amount).compareTo(BigDecimal.ZERO) > 0 ? true : false;
		 
	 }
	 
	 private void updateAccountBalances(Transactions transaction) {
		 Account fromAccount =  accountsRepository.findByAccountNumber(transaction.getTransFromAccNumber());
		 Account toAccount =  accountsRepository.findByAccountNumber(transaction.getTransToAccNumber());
		 fromAccount.setAccountBalance(fromAccount.getAccountBalance().subtract(transaction.getTransAmount()));
		 toAccount.setAccountBalance(toAccount.getAccountBalance().add(transaction.getTransAmount()));
		 accountsRepository.save(fromAccount);
		 accountsRepository.save(toAccount);
	 }
	 
}

