package uk.co.idv.uk.api.lockout.policy;

import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesConverterDelegator;
import uk.co.idv.api.lockout.policy.hard.HardLockoutPolicyAttributesConverter;
import uk.co.idv.api.lockout.policy.nonlocking.NonLockingPolicyAttributesConverter;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;

import java.util.Arrays;

public class UkLockoutPolicyAttributesConverterDelegator extends LockoutPolicyAttributesConverterDelegator {

    public UkLockoutPolicyAttributesConverterDelegator(final RecordAttemptStrategyFactory recordAttemptStrategyFactory) {
        super(Arrays.asList(
                new HardLockoutPolicyAttributesConverter(recordAttemptStrategyFactory),
                new NonLockingPolicyAttributesConverter(recordAttemptStrategyFactory)
        ));
    }

}
