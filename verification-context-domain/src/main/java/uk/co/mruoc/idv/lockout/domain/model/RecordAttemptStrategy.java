package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.lockout.domain.service.RecordAttemptRequest;

public interface RecordAttemptStrategy {

    String getType();

    boolean shouldRecordAttempt(final RecordAttemptRequest request);

}
