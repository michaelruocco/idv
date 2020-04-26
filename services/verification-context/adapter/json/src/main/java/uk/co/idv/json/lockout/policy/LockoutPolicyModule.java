package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.json.lockout.policy.hard.HardLockoutPolicyModule;
import uk.co.idv.json.lockout.policy.nonlocking.NonLockingLockoutPolicyModule;
import uk.co.idv.json.lockout.policy.soft.SoftLockoutPolicyModule;

import java.util.Arrays;

public class LockoutPolicyModule extends SimpleModule {

    public LockoutPolicyModule() {
        super("lockout-policy-module", Version.unknownVersion());

        setMixInAnnotation(LockoutPolicy.class, LockoutPolicyMixin.class);

        addDeserializer(LockoutPolicy.class, new LockoutPolicyDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new HardLockoutPolicyModule(),
                new NonLockingLockoutPolicyModule(),
                new SoftLockoutPolicyModule()
        );
    }

}
