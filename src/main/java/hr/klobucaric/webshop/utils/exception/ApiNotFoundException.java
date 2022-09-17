package hr.klobucaric.webshop.utils.exception;

public class ApiNotFoundException extends RuntimeException{

    public ApiNotFoundException() {
        super();
    }

    public ApiNotFoundException(String message) {
        super(message);
    }

    public ApiNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
