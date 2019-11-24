package uk.co.idv.repository.mongo.lockout.policy.soft;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecurringSoftLockoutPolicyDocument extends LockoutPolicyDocument {

    private SoftLockIntervalDocument interval;

}
