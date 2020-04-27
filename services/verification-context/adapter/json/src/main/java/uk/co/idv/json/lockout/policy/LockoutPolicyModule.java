package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.json.lockout.policy.hard.HardLockoutPolicyModule;
import uk.co.idv.json.lockout.policy.nonlocking.NonLockingPolicyModule;
import uk.co.idv.json.lockout.policy.soft.SoftLockoutPolicyModule;

import java.util.Arrays;

public class LockoutPolicyModule extends SimpleModule {

    public LockoutPolicyModule() {
        super("lockout-policy-module", Version.unknownVersion());

        setMixInAnnotation(LockoutPolicy.class, LockoutPolicyMixin.class);

        addDeserializer(LockoutPolicy.class, new LockoutPolicyDeserializer());
        addDeserializer(LockoutStateCalculator.class, new LockoutStateCalculatorDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new HardLockoutPolicyModule(),
                new NonLockingPolicyModule(),
                new SoftLockoutPolicyModule()
        );
    }

}
