package uk.co.idv.repository.mongo.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

public interface LockoutPolicyDocumentConverter {

    boolean supportsType(final String lockoutType);

    LockoutPolicy toPolicy(final LockoutPolicyDocument document);

    LockoutPolicyDocument toDocument(final LockoutPolicy policy);

    default LockoutLevel toLevel(final LockoutPolicyDocument document) {
        return DefaultLockoutLevel.builder()
                .activityNames(document.getActivityNames())
                .channelId(document.getChannelId())
                .aliasTypes(document.getAliasTypes())
                .build();
    }

}
