package uk.co.mruoc.idv.mongo.lockout.dao.policy;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MaxAttemptsLockoutPolicyDocument extends LockoutPolicyDocument {

    private int maxNumberOfAttempts;

}
