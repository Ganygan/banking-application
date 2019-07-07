/**
 * 
 */
package com.monese.applications.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.monese.applications.banking.model.Account;

/**
 * @author gnagarajan
 *
 */
@Repository
public interface AccountsRepository extends CrudRepository<Account, Long> {
	
	Account findByAccountNumber(Long accNumber);
	
	boolean existsByAccountNumber(Long accNumber);
	
}
