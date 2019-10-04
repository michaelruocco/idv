package uk.co.mruoc.idv.lockout.domain.service;

public class RecordNever implements RecordAttemptStrategy {

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        return false;
    }

}
