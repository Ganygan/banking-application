/**
 * 
 */
package com.monese.applications.banking.services;

import com.monese.applications.banking.exceptions.ApplicationException;
import com.monese.applications.banking.requests.AccountsTransferRequest;
import com.monese.applications.banking.responses.TransactionsResponse;

/**
 * @author gnagarajan
 *
 */
public interface TransactionsService {

	TransactionsResponse listAllTransactions(Long accountNumber) throws ApplicationException;

	void postTransactions(AccountsTransferRequest accountsTransferRequest, Long fromAccountNumber)
			throws ApplicationException;

}
