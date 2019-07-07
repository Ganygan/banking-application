/**
 * 
 */
package com.monese.applications.banking.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monese.applications.banking.exceptions.ApplicationException;
import com.monese.applications.banking.model.Account;
import com.monese.applications.banking.requests.AccountsTransferRequest;
import com.monese.applications.banking.responses.AccountDetailsResponse;
import com.monese.applications.banking.responses.TransactionsResponse;
import com.monese.applications.banking.services.AccountDetailsService;
import com.monese.applications.banking.services.TransactionsService;

/**
 * @author gnagarajan
 *
 */
//@Api(tags = "Accounts Management")
@RestController
@RequestMapping(path = "/v1/accounts")

public class BankingWebController {

	private static final Logger LOG = LogManager.getLogger(BankingWebController.class);
	
	private final AccountDetailsService accountDetailsService;
	private final TransactionsService transactionsService;
	
	  @Autowired
	  public BankingWebController(
			  AccountDetailsService accountDetailsService , TransactionsService transactionsService 
	  ) {    
	    this.accountDetailsService = accountDetailsService;
	    this.transactionsService = transactionsService;
	  }
	
	@GetMapping(path = "/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<AccountDetailsResponse> getAccountDetails(
      @PathVariable("accountNumber") Long accountNumber) throws ApplicationException {
	    LOG.entry(accountNumber);
	    try {
	      AccountDetailsResponse response = accountDetailsService.getAccountDetails(accountNumber);
	      return LOG.traceExit(new ResponseEntity<>(response, HttpStatus.OK));
	    } catch (ApplicationException ex) {
	      return LOG.traceExit(new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
	    }
	  }
	
	@PutMapping(path = "/{acountNumber}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> updateAccountDetails(
      @RequestBody @Valid Account account, @PathVariable("accountNumber") Long accountNumber) throws ApplicationException {
	    LOG.traceEntry();
	    accountDetailsService.updateAccountDetails(account,accountNumber);
	    return LOG.traceExit(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
	}
	
	@GetMapping(path = "{accountNumber}/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<TransactionsResponse> listTransactions(
    @PathVariable("accountNumber") Long accountNumber) throws ApplicationException {
	    LOG.entry(accountNumber);
	    try {
	      TransactionsResponse response = transactionsService.listAllTransactions(accountNumber);
	      return LOG.traceExit(new ResponseEntity<>(response, HttpStatus.OK));
	    } catch (ApplicationException ex) {
	      return LOG.traceExit(new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
	    }
	  }
	
	@PostMapping(path = "{accountNumber}/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Void> postTransactions(
	@RequestBody @Valid AccountsTransferRequest accountsTransferRequest,
	@PathVariable("accountNumber") Long accountNumber) throws ApplicationException {
	    LOG.entry(accountNumber);
	    try {
	      transactionsService.postTransactions(accountsTransferRequest, accountNumber);
	      return LOG.traceExit(new ResponseEntity<>(HttpStatus.CREATED));
	    } catch (ApplicationException ex) {
	      return LOG.traceExit(new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
	    }
	  }

	
}
