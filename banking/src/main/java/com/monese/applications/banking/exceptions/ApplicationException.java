/**
 * 
 */
package com.monese.applications.banking.exceptions;

/**
 * @author gnagarajan
 *
 */	
public abstract class ApplicationException extends Exception {

	  private static final long serialVersionUID = 1L;

	  protected ApplicationException( Throwable cause) {
	    super(cause);
	  }

	  protected ApplicationException() {

	  }

}


