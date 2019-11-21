package uk.co.idv.repository.mongo.lockout.policy.hard;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;

@Data
@EqualsAndHashCode(callSuper = true)
public class HardLockoutPolicyDocument extends LockoutPolicyDocument {

    private int maxNumberOfAttempts;

}
