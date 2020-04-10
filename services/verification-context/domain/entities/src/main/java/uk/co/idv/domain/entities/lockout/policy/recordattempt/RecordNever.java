package uk.co.idv.domain.entities.lockout.policy.recordattempt;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
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
