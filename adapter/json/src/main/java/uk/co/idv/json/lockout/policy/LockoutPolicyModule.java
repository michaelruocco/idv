package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.json.lockout.policy.recordattempt.DefaultRecordAttemptStrategyDeserializer;

public class LockoutPolicyModule extends SimpleModule {

    public LockoutPolicyModule() {
        setMixInAnnotation(LockoutLevel.class, LockoutLevelMixin.class);
        setMixInAnnotation(LockoutPolicy.class, LockoutPolicyMixin.class);

        addDeserializer(LockoutLevel.class, new LockoutLevelDeserializer());
        addDeserializer(RecordAttemptStrategy.class, new DefaultRecordAttemptStrategyDeserializer());
        addDeserializer(LockoutPolicy.class, new DefaultLockoutPolicyDeserializer());
    }

}
