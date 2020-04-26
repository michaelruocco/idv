package uk.co.idv.uk.api.lockout.policy;

import uk.co.idv.api.lockout.policy.DefaultLockoutPolicyAttributesConverter;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;

public class UkLockoutPolicyAttributesConverter extends DefaultLockoutPolicyAttributesConverter {

    public UkLockoutPolicyAttributesConverter(final RecordAttemptStrategyFactory recordAttemptStrategyFactory) {
        super(new UkLockoutPolicyAttributesConverterDelegator(recordAttemptStrategyFactory));
    }

}
