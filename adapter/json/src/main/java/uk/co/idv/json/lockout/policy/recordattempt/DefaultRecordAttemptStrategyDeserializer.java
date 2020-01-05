package uk.co.idv.json.lockout.policy.recordattempt;

import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;

public class DefaultRecordAttemptStrategyDeserializer extends RecordAttemptStrategyDeserializer {

    public DefaultRecordAttemptStrategyDeserializer() {
        super(new RecordAttemptStrategyFactory());
    }

}
