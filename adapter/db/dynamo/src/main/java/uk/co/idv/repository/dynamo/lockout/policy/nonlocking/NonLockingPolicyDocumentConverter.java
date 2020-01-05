package uk.co.idv.repository.dynamo.lockout.policy.nonlocking;

import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutStateCalculator;
import uk.co.idv.repository.dynamo.json.JsonConverter;
import uk.co.idv.repository.dynamo.lockout.policy.AbstractLockoutPolicyDocumentConverter;

public class NonLockingPolicyDocumentConverter extends AbstractLockoutPolicyDocumentConverter<NonLockingLockoutPolicy> {

    public NonLockingPolicyDocumentConverter(final JsonConverter jsonConverter) {
        super(NonLockingLockoutStateCalculator.TYPE, NonLockingLockoutPolicy.class, jsonConverter);
    }

}
