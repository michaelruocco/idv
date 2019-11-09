package uk.co.idv.domain.entities.lockout;

import uk.co.idv.domain.usecases.lockout.RecordAttemptRequest;

public interface RecordAttemptStrategy {

    String getType();

    boolean shouldRecordAttempt(final RecordAttemptRequest request);

}
