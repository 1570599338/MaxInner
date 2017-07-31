/**
 * 
 */
package com.lquan.POI;

/**
 * @author lizheng
 *
 */
public class POIException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public POIException() {
		
	}

	/**
	 * @param message
	 */
	public POIException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public POIException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public POIException(String message, Throwable cause) {
		super(message, cause);
	}

}
