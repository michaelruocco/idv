package uk.co.mruoc.idv.lockout.domain.service;

public class RecordEveryAttempt implements RecordAttemptStrategy {

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        return true;
    }

}
