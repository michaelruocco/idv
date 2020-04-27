package uk.co.idv.json.lockout.policy.hard;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.json.lockout.policy.LockoutPolicyMixin;
import uk.co.idv.json.lockout.policy.level.LockoutLevelModule;
import uk.co.idv.json.lockout.policy.recordattempt.RecordAttemptStrategyModule;

import java.util.Arrays;

public class HardLockoutPolicyModule extends SimpleModule {

    public HardLockoutPolicyModule() {
        super("hard-lockout-policy-module", Version.unknownVersion());

        setMixInAnnotation(LockoutPolicy.class, LockoutPolicyMixin.class);

        addDeserializer(HardLockoutStateCalculator.class, new HardLockoutStateCalculatorDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new LockoutLevelModule(),
                new RecordAttemptStrategyModule()
        );
    }

}
