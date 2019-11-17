package uk.co.idv.repository.mongo.lockout.policy.maxattempts;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;

@Data
@EqualsAndHashCode(callSuper = true)
public class MaxAttemptsLockoutPolicyDocument extends LockoutPolicyDocument {

    private int maxNumberOfAttempts;

}
