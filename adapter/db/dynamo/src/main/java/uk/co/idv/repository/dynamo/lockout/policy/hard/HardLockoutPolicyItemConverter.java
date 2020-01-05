package uk.co.idv.repository.dynamo.lockout.policy.hard;

import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.repository.dynamo.json.JsonConverter;
import uk.co.idv.repository.dynamo.lockout.policy.AbstractLockoutPolicyItemConverter;

public class HardLockoutPolicyItemConverter extends AbstractLockoutPolicyItemConverter<HardLockoutPolicy> {

    public HardLockoutPolicyItemConverter(final JsonConverter jsonConverter) {
        super(HardLockoutStateCalculator.TYPE, HardLockoutPolicy.class, jsonConverter);
    }

}
