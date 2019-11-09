package uk.co.idv.repository.mongo.lockout.policy;

import uk.co.mruoc.idv.lockout.domain.model.AliasLevelLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

public class LockoutPolicyDocumentKeyConverter {

    public static String toKey(final AliasLevelLockoutPolicyParameters parameters) {
        return toKey(parameters.getChannelId(),
                parameters.getActivityName(),
                parameters.getAliasType());
    }

    public static String toKey(final LockoutRequest request) {
        return toKey(request.getChannelId(),
                request.getActivityName(),
                request.getAliasType());
    }

    public static String toKey(final String channelId,
                               final String activityName,
                               final String aliasType) {
        return String.format("%s*%s*%s",
                channelId,
                activityName,
                aliasType);
    }

    public static String extractChannelId(final String key) {
        return splitKey(key)[0];
    }

    public static String extractActivityName(final String key) {
        return splitKey(key)[1];
    }

    public static String extractAliasType(final String key) {
        return splitKey(key)[2];
    }

    public static String[] splitKey(final String key) {
        return key.split("\\*");
    }

}
