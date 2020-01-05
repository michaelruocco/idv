package uk.co.idv.repository.dynamo.lockout.policy.soft;

import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.repository.dynamo.json.JsonConverter;
import uk.co.idv.repository.dynamo.lockout.policy.AbstractLockoutPolicyDocumentConverter;

public class RecurringSoftLockoutPolicyItemConverter extends AbstractLockoutPolicyDocumentConverter<RecurringSoftLockoutPolicy> {

    public RecurringSoftLockoutPolicyItemConverter(final JsonConverter jsonConverter) {
        super(RecurringSoftLockoutStateCalculator.TYPE, RecurringSoftLockoutPolicy.class, jsonConverter);
    }

}