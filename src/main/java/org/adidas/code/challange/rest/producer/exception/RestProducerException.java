package org.adidas.code.challange.rest.producer.exception;

public class RestProducerException extends RuntimeException {

	private static final long serialVersionUID = 5221230516561029932L;

	public RestProducerException() {
		super();
	}

	public RestProducerException(Exception e) {
		super(e);
	}

	public RestProducerException(String message) {
		super(message);
	}

	public RestProducerException(String message, Exception cause) {
		super(message, cause);
	}

}
