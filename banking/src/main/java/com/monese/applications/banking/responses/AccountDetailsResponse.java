/**
 * 
 */
package com.monese.applications.banking.responses;

import com.monese.applications.banking.model.Account;

/**
 * @author gnagarajan
 *
 */
public class AccountDetailsResponse {

	private Account account;

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}
	
}
