package hr.klobucaric.webshop.utils.exception;

public class UserAlreadyExistException extends RuntimeException{

	public UserAlreadyExistException() {
		super();
	}

	public UserAlreadyExistException(String message){
		super(message);
	}

	public UserAlreadyExistException(String message, Throwable cause){
		super(message, cause);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
