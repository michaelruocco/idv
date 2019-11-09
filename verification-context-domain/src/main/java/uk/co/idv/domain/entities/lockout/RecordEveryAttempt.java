package uk.co.idv.domain.entities.lockout;

import uk.co.idv.domain.usecases.lockout.RecordAttemptRequest;

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
