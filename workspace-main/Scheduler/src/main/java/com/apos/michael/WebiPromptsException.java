package com.apos.michael;

public class WebiPromptsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public WebiPromptsException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public WebiPromptsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public WebiPromptsException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public WebiPromptsException(Throwable cause) {
		super(cause);
	}
}
