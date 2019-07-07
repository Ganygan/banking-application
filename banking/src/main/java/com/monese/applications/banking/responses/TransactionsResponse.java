package com.monese.applications.banking.responses;

import java.util.List;

import com.monese.applications.banking.model.Transactions;

public class TransactionsResponse {
	
	public List<Transactions> listTransactions;

	/**
	 * @return the listTransactions
	 */
	public List<Transactions> getListTransactions() {
		return listTransactions;
	}

	/**
	 * @param listTransactions the listTransactions to set
	 */
	public void setListTransactions(List<Transactions> listTransactions) {
		this.listTransactions = listTransactions;
	}

}
