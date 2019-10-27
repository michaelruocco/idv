package uk.co.mruoc.idv.lockout.domain.service;

public class RecordNever implements RecordAttemptStrategy {

    public static final String TYPE = "never";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        return false;
    }

}
