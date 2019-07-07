/**
 * 
 */
package com.monese.applications.banking.services;

import com.monese.applications.banking.model.Account;
import com.monese.applications.banking.responses.AccountDetailsResponse;

/**
 * @author gnagarajan
 *
 */
public interface AccountDetailsService {

	public AccountDetailsResponse getAccountDetails(Long accountNumber) throws com.monese.applications.banking.exceptions.ApplicationException;
	
	public void updateAccountDetails(Account account, Long accountNumber) throws com.monese.applications.banking.exceptions.ApplicationException;
	
}
