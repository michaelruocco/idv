package uk.co.idv.json.lockout.policy.soft;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervals;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutStateCalculator;
import uk.co.idv.json.DurationSerializer;
import uk.co.idv.json.lockout.policy.LockoutPolicyMixin;
import uk.co.idv.json.lockout.policy.level.LockoutLevelModule;
import uk.co.idv.json.lockout.policy.recordattempt.RecordAttemptStrategyModule;

import java.time.Duration;
import java.util.Arrays;

public class SoftLockoutPolicyModule extends SimpleModule {

    public SoftLockoutPolicyModule() {
        super("soft-lockout-policy-module", Version.unknownVersion());

        setMixInAnnotation(LockoutPolicy.class, LockoutPolicyMixin.class);

        addSerializer(Duration.class, new DurationSerializer());

        addDeserializer(SoftLockInterval.class, new SoftLockIntervalDeserializer());
        addDeserializer(SoftLockIntervals.class, new SoftLockIntervalsDeserializer());

        addDeserializer(SoftLockoutStateCalculator.class, new SoftLockoutStateCalculatorDeserializer());
        addDeserializer(RecurringSoftLockoutStateCalculator.class, new RecurringSoftLockoutStateCalculatorDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new LockoutLevelModule(),
                new RecordAttemptStrategyModule()
        );
    }

}
