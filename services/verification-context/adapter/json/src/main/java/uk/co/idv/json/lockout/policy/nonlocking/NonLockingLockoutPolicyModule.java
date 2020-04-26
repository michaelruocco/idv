package uk.co.idv.json.lockout.policy.nonlocking;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutPolicy;
import uk.co.idv.json.lockout.policy.LockoutPolicyMixin;
import uk.co.idv.json.lockout.policy.level.LockoutLevelModule;
import uk.co.idv.json.lockout.policy.recordattempt.RecordAttemptStrategyModule;

import java.util.Arrays;

public class NonLockingLockoutPolicyModule extends SimpleModule {

    public NonLockingLockoutPolicyModule() {
        super("non-locking-lockout-policy-module", Version.unknownVersion());

        setMixInAnnotation(LockoutPolicy.class, LockoutPolicyMixin.class);

        addDeserializer(NonLockingLockoutPolicy.class, new NonLockingLockoutPolicyDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new LockoutLevelModule(),
                new RecordAttemptStrategyModule()
        );
    }

}
