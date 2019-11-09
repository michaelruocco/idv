package uk.co.idv.repository.mongo.lockout.policy;

import uk.co.idv.domain.entities.lockout.LockoutPolicyParameters;

public interface LockoutPolicyDocumentConverter {

    boolean supportsType(final String lockoutType);

    LockoutPolicyParameters toParameters(final LockoutPolicyDocument document);

    LockoutPolicyDocument toDocument(final LockoutPolicyParameters parameters);

}
