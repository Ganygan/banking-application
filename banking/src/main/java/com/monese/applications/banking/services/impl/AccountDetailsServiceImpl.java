/**
 * 
 */
package com.monese.applications.banking.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monese.applications.banking.exceptions.ApplicationException;
import com.monese.applications.banking.model.Account;
import com.monese.applications.banking.repository.AccountsRepository;
import com.monese.applications.banking.responses.AccountDetailsResponse;
import com.monese.applications.banking.services.AccountDetailsService;

/**
 * @author gnagarajan
 *
 */
@Service
public class AccountDetailsServiceImpl implements AccountDetailsService 
{
	@Autowired
	protected AccountDetailsServiceImpl() {
		
	}
	
	private static final Logger LOG = LogManager.getLogger(AccountDetailsServiceImpl.class);
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	  public AccountDetailsResponse getAccountDetails(Long accountNumber) throws ApplicationException {
	    LOG.entry(accountNumber);
	
	    AccountDetailsResponse accountDetailsResponse = new AccountDetailsResponse();
	    
	    try {
	    Account account = accountsRepository.findByAccountNumber(accountNumber);
	    LOG.debug(account.getAccountDisplayName());
	    accountDetailsResponse.setAccount(account);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return LOG.traceExit(accountDetailsResponse);
	  }
	  
	  public void updateAccountDetails(Account account, Long accountNumber) throws ApplicationException {
	    LOG.entry(accountNumber);
	    try {
	    	if (accountsRepository.existsByAccountNumber(accountNumber) ) {
	    		accountsRepository.save(account);
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	     LOG.traceExit();
	  }	  
}
