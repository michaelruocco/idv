package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.lockout.domain.service.RecordAttemptRequest;

public class RecordEveryAttempt implements RecordAttemptStrategy {

    public static final String TYPE = "every-attempt";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        return true;
    }

}
