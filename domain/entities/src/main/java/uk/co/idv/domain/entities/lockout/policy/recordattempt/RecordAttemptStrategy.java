package uk.co.idv.domain.entities.lockout.policy.recordattempt;

public interface RecordAttemptStrategy {

    String getType();

    boolean shouldRecordAttempt(final RecordAttemptRequest request);

}
