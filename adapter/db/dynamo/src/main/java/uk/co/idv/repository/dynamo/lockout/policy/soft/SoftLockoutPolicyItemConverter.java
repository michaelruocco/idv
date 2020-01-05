package uk.co.idv.repository.dynamo.lockout.policy.soft;

import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutStateCalculator;
import uk.co.idv.repository.dynamo.json.JsonConverter;
import uk.co.idv.repository.dynamo.lockout.policy.AbstractLockoutPolicyItemConverter;

public class SoftLockoutPolicyItemConverter extends AbstractLockoutPolicyItemConverter<SoftLockoutPolicy> {

    public SoftLockoutPolicyItemConverter(final JsonConverter jsonConverter) {
        super(SoftLockoutStateCalculator.TYPE, SoftLockoutPolicy.class, jsonConverter);
    }

}