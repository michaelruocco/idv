package uk.co.mruoc.idv.lockout.domain.model;

public class RecordAttemptStrategyFactory {

    public RecordAttemptStrategy build(final String type) {
        switch (type) {
            case RecordEveryAttempt.TYPE:
                return new RecordEveryAttempt();
            case RecordNever.TYPE:
                return new RecordNever();
            case RecordOnMethodComplete.TYPE:
                return new RecordOnMethodComplete();
            case RecordOnSequenceComplete.TYPE:
                return new RecordOnSequenceComplete();
            default:
                throw new RecordAttemptStrategyNotSupportedException(type);
        }
    }

    public static class RecordAttemptStrategyNotSupportedException extends RuntimeException {

        public RecordAttemptStrategyNotSupportedException(final String type) {
            super(type);
        }

    }

}
