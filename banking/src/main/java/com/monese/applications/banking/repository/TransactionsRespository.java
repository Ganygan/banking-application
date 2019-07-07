/**
 * 
 */
package com.monese.applications.banking.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.monese.applications.banking.model.Transactions;

/**
 * @author gnagarajan
 *
 */
public interface TransactionsRespository extends CrudRepository<Transactions, Transactions> {

	List<Transactions> findByTransFromAccNumberOrTransToAccNumber(Long accountNumber1, Long accountNumber2);
	
}
