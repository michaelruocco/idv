package uk.co.idv.domain.usecases.exception;

public class MethodNotSupportedException extends RuntimeException {

    public MethodNotSupportedException(final String name) {
        super(name);
    }

}
