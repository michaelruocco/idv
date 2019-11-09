package uk.co.idv.repository.mongo.lockout.policy;

import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;

public interface LockoutPolicyDocumentConverter {

    boolean supportsType(final String lockoutType);

    LockoutPolicyParameters toParameters(final LockoutPolicyDocument document);

    LockoutPolicyDocument toDocument(final LockoutPolicyParameters parameters);

}
