package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.json.lockout.policy.recordattempt.DefaultRecordAttemptStrategyDeserializer;
import uk.co.idv.json.lockout.policy.soft.SoftLockIntervalDeserializer;

public class LockoutPolicyModule extends SimpleModule {

    public LockoutPolicyModule() {
        setMixInAnnotation(LockoutLevel.class, LockoutLevelMixin.class);
        setMixInAnnotation(LockoutPolicy.class, LockoutPolicyMixin.class);

        addDeserializer(SoftLockInterval.class, new SoftLockIntervalDeserializer());
        addDeserializer(LockoutLevel.class, new LockoutLevelDeserializer());
        addDeserializer(RecordAttemptStrategy.class, new DefaultRecordAttemptStrategyDeserializer());
        addDeserializer(LockoutPolicy.class, new DefaultLockoutPolicyDeserializer());
    }

}
