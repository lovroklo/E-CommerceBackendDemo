package hr.klobucaric.webshop.utils.exception;

public class ApiNoContentException extends RuntimeException {
    public ApiNoContentException() {
        super();
    }

    public ApiNoContentException(String message){
        super(message);
    }

    public ApiNoContentException(String message, Throwable cause){
        super(message, cause);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
