package uk.co.mruoc.idv.domain.exception;

public class ActivityNotSupportedException extends RuntimeException {

    public ActivityNotSupportedException(final String name) {
        super(name);
    }

}
