/**
 * 
 */
package com.monese.applications.banking.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author gnagarajan
 *
 */
public final class Money implements Comparable<Money>, Serializable  {

	/*
	 * When the interface of the class changes change the serialVersionUID
	 */
	private static final long serialVersionUID = 4511421804517171977L;
	
	private static final int AMOUNT_SCALE = 2;
	private static final String ZERO_VALUE = "0";
	private static final String MONEY_ZERO = "0.00";
	
	public static final Money ZERO = new Money(ZERO_VALUE);
	
	private BigDecimal amount;
	
	/**
	 * Create a new Money object. Amount will be rounded to two decimal places.
	 */
	public Money(double amount) {
		this(new BigDecimal(amount));
	}

	/**
	 * Create a new Money object. Amount will be rounded to two decimal places.
	 */
	public Money(BigDecimal amount) {
		if (amount == null) {
			throw new IllegalArgumentException("amount cannot be null");
		}
		this.amount = amount.setScale(AMOUNT_SCALE, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * Create a new Money object. Amount will be rounded to two decimal places.
	 */
	public Money(String amount) {
		this(new BigDecimal(amount));
	}
	
	/**
	 * Retrieve the Money amount as a BigDecimal.
	 */
	public BigDecimal toBigDecimal() {
		return new BigDecimal(amount.toString());
	}
	
	/**
	 * Retrieve the Money amount as a BigDecimal without trailing zeroes.
	 */
	public BigDecimal toBigDecimalWithoutTrailingZeroes() {
		String stringAmount = amount.toString();
		if (MONEY_ZERO.equals(stringAmount)) {
			return new BigDecimal(ZERO_VALUE);
		}
		while (stringAmount.endsWith(ZERO_VALUE)) {
			stringAmount = stringAmount.substring(0, stringAmount.length() - 1);
		}
		
		return new BigDecimal(stringAmount);
	}
	
	/**
	 * Adds money to more money
	 * @param moneyToAdd
	 * @return
	 */
	public Money add(Money moneyToAdd) {
		return new Money(amount.add(moneyToAdd.toBigDecimal()));
	}
	
	/**
	 * Subtracts money
	 * @param moneyToSubtract
	 * @return subtracted amount
	 */
	public Money subtract(Money moneyToSubtract) {
		return new Money(amount.subtract(moneyToSubtract.toBigDecimal()));
	}
	
	/**
	 * multiples the money amount by a given value
	 * @param value to times by
	 * @return new Money of multiplied amount
	 */
	public Money multiplyBy(int i) {
		return new Money(amount.multiply(new BigDecimal(i)));
	}
	
	public Money multiplyBy(Money money) {
		return new Money(amount.multiply(money.amount ));
	}
	
	public Money multiplyBy(BigDecimal percentage) {
		return new Money(amount.multiply(percentage).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP));
	}
	
	public Money divideBy(int i) {
		return new Money(amount.divide(new BigDecimal(i), BigDecimal.ROUND_HALF_UP));
	}
	
	public Money divideBy(Money money) {
		return new Money(amount.divide(money.amount, 2, BigDecimal.ROUND_HALF_UP));
	}

	/**
	 * Determine if two <code>Money</code> objects are the same by comparing the amounts.
	 * @param o the object to compare to
	 * @return <code>true</code> if this <code>Money</code> is the same as the o argument.
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (o.getClass() != getClass()) {
			return false;
		}
		Money castedObj = (Money) o;
		
		return this.amount.equals(castedObj.amount);
	}
	
	public int hashCode() {
		return amount.hashCode();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[Money: amount = " + amount.toString() + "]";
	}

	public int compareTo(Money compareAmount) {
		if (compareAmount == null) {
			return 1;
		}
		return amount.compareTo(compareAmount.toBigDecimal());
	}

	public Money max(Money otherMoney) {
		return new Money(amount.max(otherMoney.amount));
	}
	
	public Money min(Money otherMoney) {
		return new Money(amount.min(otherMoney.amount));
	}

}