package uk.co.idv.domain.entities.lockout.policy.recordattempt;

public class RecordNever implements RecordAttemptStrategy {

    public static final String TYPE = "never";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        return false;
    }

}
