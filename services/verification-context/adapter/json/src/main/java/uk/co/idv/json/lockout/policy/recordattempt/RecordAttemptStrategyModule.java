package uk.co.idv.json.lockout.policy.recordattempt;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;

public class RecordAttemptStrategyModule extends SimpleModule {

    public RecordAttemptStrategyModule() {
        super("record-attempt-strategy-module", Version.unknownVersion());

        addDeserializer(RecordAttemptStrategy.class, new DefaultRecordAttemptStrategyDeserializer());
    }

}
