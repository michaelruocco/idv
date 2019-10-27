package uk.co.mruoc.idv.lockout.domain.service;

public interface RecordAttemptStrategy {

    String getType();

    boolean shouldRecordAttempt(final RecordAttemptRequest request);

}
