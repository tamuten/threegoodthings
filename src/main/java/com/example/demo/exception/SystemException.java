package com.example.demo.exception;

/**
 * システム例外クラス
 *
 * @author takashi1
 *
 */
public class SystemException extends RuntimeException {
	public SystemException() {
		super();
	}

	public SystemException(String msg) {
		super(msg);
	}
}
