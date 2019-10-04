package uk.co.mruoc.idv.lockout.domain.service;

public interface RecordAttemptStrategy {

    boolean shouldRecordAttempt(final RecordAttemptRequest request);

}
