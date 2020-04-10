package uk.co.idv.domain.usecases.exception;

public class ActivityNotSupportedException extends RuntimeException {

    public ActivityNotSupportedException(final String name) {
        super(name);
    }

}
