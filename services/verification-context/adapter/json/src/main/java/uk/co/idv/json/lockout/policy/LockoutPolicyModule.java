package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.json.lockout.policy.recordattempt.DefaultRecordAttemptStrategyDeserializer;
import uk.co.idv.json.lockout.policy.soft.SoftLockIntervalDeserializer;
import uk.co.idv.json.lockout.policy.soft.SoftLockIntervalSerializer;

public class LockoutPolicyModule extends SimpleModule {

    public LockoutPolicyModule() {
        super("lockout-policy-module", Version.unknownVersion());

        setMixInAnnotation(LockoutLevel.class, LockoutLevelMixin.class);
        setMixInAnnotation(LockoutPolicy.class, LockoutPolicyMixin.class);

        addSerializer(SoftLockInterval.class, new SoftLockIntervalSerializer());

        addDeserializer(SoftLockInterval.class, new SoftLockIntervalDeserializer());
        addDeserializer(LockoutLevel.class, new LockoutLevelDeserializer());
        addDeserializer(RecordAttemptStrategy.class, new DefaultRecordAttemptStrategyDeserializer());
        addDeserializer(LockoutPolicy.class, new DefaultLockoutPolicyDeserializer());
    }

}
