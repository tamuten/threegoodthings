package com.example.demo.exception;

/**
 * ビジネス例外クラス
 *
 * @author takashi1
 *
 */
public class BusinessException extends RuntimeException {
	public BusinessException() {
		super();
	}

	public BusinessException(String msg) {
		super(msg);
	}
}
