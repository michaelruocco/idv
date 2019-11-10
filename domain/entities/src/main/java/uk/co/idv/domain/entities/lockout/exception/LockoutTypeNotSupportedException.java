package uk.co.idv.domain.entities.lockout.exception;

public class LockoutTypeNotSupportedException extends RuntimeException {

    public LockoutTypeNotSupportedException(final String type) {
        super(type);
    }

}
