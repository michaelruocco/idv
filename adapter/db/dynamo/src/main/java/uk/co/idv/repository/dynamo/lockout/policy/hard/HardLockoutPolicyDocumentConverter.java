package uk.co.idv.repository.dynamo.lockout.policy.hard;

import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.repository.dynamo.json.JsonConverter;
import uk.co.idv.repository.dynamo.lockout.policy.AbstractLockoutPolicyDocumentConverter;

public class HardLockoutPolicyDocumentConverter extends AbstractLockoutPolicyDocumentConverter<HardLockoutPolicy> {

    public HardLockoutPolicyDocumentConverter(final JsonConverter jsonConverter) {
        super(HardLockoutStateCalculator.TYPE, HardLockoutPolicy.class, jsonConverter);
    }

}
