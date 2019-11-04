package uk.co.mruoc.idv.mongo.lockout.dao.policy;

import uk.co.mruoc.idv.lockout.domain.model.AbstractLockoutPolicyParameters;

public interface LockoutPolicyDocumentConverter {

    boolean supportsType(final String lockoutType);

    AbstractLockoutPolicyParameters toParameters(final LockoutPolicyDocument document);

    LockoutPolicyDocument toDocument(final AbstractLockoutPolicyParameters parameters);

}
