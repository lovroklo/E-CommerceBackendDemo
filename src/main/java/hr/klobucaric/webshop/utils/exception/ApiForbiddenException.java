package hr.klobucaric.webshop.utils.exception;

public class ApiForbiddenException extends RuntimeException{

	public ApiForbiddenException() {
		super();
	}

	public ApiForbiddenException(String message){
		super(message);
	}

	public ApiForbiddenException(String message, Throwable cause){
		super(message, cause);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
