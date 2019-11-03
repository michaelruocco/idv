package uk.co.mruoc.idv.mongo.lockout.dao;

import uk.co.mruoc.idv.lockout.domain.model.AbstractLockoutPolicyParameters;

public interface LockoutPolicyParametersConverter {

    boolean supportsType(final String lockoutType);

    AbstractLockoutPolicyParameters toParameters(final LockoutPolicyDocument document);

    LockoutPolicyDocument toDocument(final AbstractLockoutPolicyParameters parameters);

}
