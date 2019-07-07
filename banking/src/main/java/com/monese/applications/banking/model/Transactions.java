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

/**
 * @author gnagarajan
 *
 */
@Entity
@Table(name = "Transactions")
public class Transactions {
	
	@Id
    @GeneratedValue
    @Column(name = "trans_id", nullable = false)
    private Long trans_id;

	@Column(name = "trans_from_acc_number", length = 12, nullable = false)
    private Long transFromAccNumber;
	
	@Column(name = "trans_to_acc_number", length = 12, nullable = false)
    private Long transToAccNumber;

	@Column(name = "trans_amount", columnDefinition="Decimal(12,2)")
    private BigDecimal transAmount;
	
	@Column(name = "trans_status", length = 1)
    private String transStatus;
	
	@Column(name = "trans_type", length = 100)
    private String transType;
	
	@Column(name = "trans_Reference", length = 250)
    private String transReference;

	public Long getTransFromAccNumber() {
		return transFromAccNumber;
	}

	public void setTransFromAccNumber(Long transFromAccNumber) {
		this.transFromAccNumber = transFromAccNumber;
	}

	public Long getTransToAccNumber() {
		return transToAccNumber;
	}

	public void setTransToAccNumber(Long transToAccNumber) {
		this.transToAccNumber = transToAccNumber;
	}

	public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransReference() {
		return transReference;
	}

	public void setTransReference(String transReference) {
		this.transReference = transReference;
	}

	public Long getTrans_id() {
		return trans_id;
	}
	
	
	
	
}
