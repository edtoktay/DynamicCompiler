/**
 * 
 */
package org.edtoktay.dynamic.compiler.exceptions;

/**
 * @author deniz.toktay
 *
 */
public class NotImplementedException extends UnsupportedOperationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NotImplementedException() {
	}

	/**
	 * @param message
	 */
	public NotImplementedException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public NotImplementedException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NotImplementedException(String message, Throwable cause) {
		super(message, cause);
	}

}
