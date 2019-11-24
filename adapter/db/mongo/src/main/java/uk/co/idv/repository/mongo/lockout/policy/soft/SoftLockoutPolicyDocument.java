package uk.co.idv.repository.mongo.lockout.policy.soft;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class SoftLockoutPolicyDocument extends LockoutPolicyDocument {

    private Collection<SoftLockIntervalDocument> intervals;

}
