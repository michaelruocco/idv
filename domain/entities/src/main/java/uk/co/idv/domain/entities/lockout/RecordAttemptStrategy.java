package uk.co.idv.domain.entities.lockout;

public interface RecordAttemptStrategy {

    String getType();

    boolean shouldRecordAttempt(final RecordAttemptRequest request);

}
