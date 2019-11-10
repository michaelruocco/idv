package uk.co.idv.repository.mongo.lockout.policy;

import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.policy.AliasLockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

import java.util.Arrays;
import java.util.Collection;

public class LockoutPolicyDocumentKeyConverter {

    private static final String ALL_ALIASES = "ALL";

    public String toKey(final LockoutLevel level) {
        if (level instanceof AliasLockoutLevel) {
            return toAliasKey((AliasLockoutLevel) level);
        }
        return toDefaultKey((DefaultLockoutLevel) level);
    }

    public Collection<String> toKeys(final LockoutRequest request) {
        return Arrays.asList(
                toKey(request.getChannelId(), request.getActivityName(), request.getAliasType()),
                toKey(request.getChannelId(), request.getActivityName())
        );
    }

    public LockoutLevel toLevel(final String key) {
        String channelId = extractChannelId(key);
        String activityName = extractActivityName(key);
        String aliasType = extractAliasType(key);
        if (aliasType.equals(ALL_ALIASES)) {
            return DefaultLockoutLevel.builder()
                    .channelId(channelId)
                    .activityName(activityName)
                    .build();
        }
        return AliasLockoutLevel.builder()
                .channelId(channelId)
                .activityName(activityName)
                .aliasType(aliasType)
                .build();
    }

    private static String toDefaultKey(DefaultLockoutLevel level) {
        return toKey(level.getChannelId(), level.getActivityName());
    }

    private static String toAliasKey(AliasLockoutLevel level) {
        return toKey(level.getChannelId(), level.getActivityName(), level.getAliasType());
    }

    private static String toKey(String channelId, String activityName) {
        return toKey(channelId, activityName, ALL_ALIASES);
    }

    private static String toKey(String channelId, String activityName, String aliasType) {
        return String.format("%s*%s*%s", channelId, activityName, aliasType);
    }

    private static String extractChannelId(final String key) {
        return splitKey(key)[0];
    }

    private static String extractActivityName(final String key) {
        return splitKey(key)[1];
    }

    private static String extractAliasType(final String key) {
        return splitKey(key)[2];
    }

    private static String[] splitKey(final String key) {
        return key.split("\\*");
    }

}
