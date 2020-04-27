package uk.co.idv.json.lockout.policy.nonlocking;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingStateCalculator;
import uk.co.idv.json.lockout.policy.LockoutPolicyMixin;
import uk.co.idv.json.lockout.policy.level.LockoutLevelModule;
import uk.co.idv.json.lockout.policy.recordattempt.RecordAttemptStrategyModule;

import java.util.Arrays;

public class NonLockingPolicyModule extends SimpleModule {

    public NonLockingPolicyModule() {
        super("non-locking-lockout-policy-module", Version.unknownVersion());

        setMixInAnnotation(LockoutPolicy.class, LockoutPolicyMixin.class);

        addDeserializer(NonLockingStateCalculator.class, new NonLockingStateCalculatorDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new LockoutLevelModule(),
                new RecordAttemptStrategyModule()
        );
    }

}
