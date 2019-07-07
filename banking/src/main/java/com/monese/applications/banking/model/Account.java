/**
 * 
 */
package com.monese.applications.banking.model;


import java.math.BigDecimal;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * @author gnagarajan
 *
 */
@Entity
@DynamicUpdate
@Table(name = "Accounts")
public class Account {
	

	@Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

	@Column(name = "acc_number", length = 12, nullable = false, unique = true)
    private Long accountNumber;
	
	@Column(name = "acc_sortcode", length = 6, nullable = false)
    private Long accountSortCode;
	
	@Column(name = "acc_name", length = 250, nullable = false)
    private String accountName;
	
	@Column(name = "acc_display_name", length = 250)
    private String accountDisplayName;
	
	@Column(name = "acc_type", length = 250)
    private String accountType;
	
	@Column(name = "acc_bank_society", length = 2)
    private String accountSociety;
	
	@Column(name = "acc_balance", columnDefinition="Decimal(12,2)")
    private BigDecimal accountBalance;

	
	public Account() {}
	
	Account(Long id, Long accNumber, Long sortcode, String accName, String accDisplayName, String accType, String accSociety, BigDecimal balance) {
		this.id = id;
		this.accountNumber = accNumber;
		this.accountSortCode = sortcode;
		this.accountName = accName;
		this.accountDisplayName = accDisplayName;
		this.accountType = accType;
		this.accountSociety = accSociety;
		this.accountBalance = balance;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountDisplayName() {
		return accountDisplayName;
	}

	public void setAccountDisplayName(String accountDisplayName) {
		this.accountDisplayName = accountDisplayName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountSociety() {
		return accountSociety;
	}

	public void setAccountSociety(String accountSociety) {
		this.accountSociety = accountSociety;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getAccountSortCode() {
		return accountSortCode;
	}

	public void setAccountSortCode(Long accountSortCode) {
		this.accountSortCode = accountSortCode;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Long getId() {
		return id;
	}

	
}	
