package uk.co.mruoc.idv.domain.exception;

public class MethodNotSupportedException extends RuntimeException {

    public MethodNotSupportedException(final String name) {
        super(name);
    }

}
