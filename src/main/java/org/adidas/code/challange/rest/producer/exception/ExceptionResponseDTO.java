package org.adidas.code.challange.rest.producer.exception;

public class ExceptionResponseDTO extends RuntimeException {

	private static final long serialVersionUID = -1031432443437035289L;

	public ExceptionResponseDTO() {
		super();
	}
	
	public ExceptionResponseDTO(Exception e) {
		super(e);
	}

	public ExceptionResponseDTO(String message) {
		super(message);
	}

	public ExceptionResponseDTO(String message, Exception cause) {
		super(message, cause);
	}

}
