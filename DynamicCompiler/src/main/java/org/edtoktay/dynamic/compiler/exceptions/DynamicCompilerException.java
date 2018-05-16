/**
 * 
 */
package org.edtoktay.dynamic.compiler.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author deniz.toktay
 *
 */
public class DynamicCompilerException extends Exception {
	private static final Logger log = LoggerFactory.getLogger(DynamicCompilerException.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DynamicCompilerException() {
		log.error("Dynamic Compiler Exception Occured");
	}

	/**
	 * @param message
	 */
	public DynamicCompilerException(String message) {
		super(message);
		log.error(message);
	}

	/**
	 * @param cause
	 */
	public DynamicCompilerException(Throwable cause) {
		super(cause);
		log.error("", cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DynamicCompilerException(String message, Throwable cause) {
		super(message, cause);
		log.error(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DynamicCompilerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		log.error(message, cause);
	}

}
