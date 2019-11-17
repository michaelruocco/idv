package uk.co.idv.repository.mongo.lockout.policy;

import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.policy.AliasLockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class LockoutPolicyDocumentKeyConverter {

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
        final String channelId = extractChannelId(key);
        final String activityName = extractActivityName(key);
        final Optional<String> aliasType = extractAliasType(key);
        if (aliasType.isPresent()) {
            return AliasLockoutLevel.builder()
                    .channelId(channelId)
                    .activityName(activityName)
                    .aliasType(aliasType.get())
                    .build();
        }
        return DefaultLockoutLevel.builder()
                .channelId(channelId)
                .activityName(activityName)
                .build();
    }

    private static String toDefaultKey(final DefaultLockoutLevel level) {
        return toKey(level.getChannelId(), level.getActivityName());
    }

    private static String toAliasKey(final AliasLockoutLevel level) {
        return toKey(level.getChannelId(), level.getActivityName(), level.getAliasType());
    }

    private static String toKey(final String channelId, final String activityName, final String aliasType) {
        return String.format("%s*%s*%s", channelId, activityName, aliasType);
    }

    private static String toKey(final String channelId, final String activityName) {
        return String.format("%s*%s", channelId, activityName);
    }

    private static String extractChannelId(final String key) {
        return splitKey(key)[0];
    }

    private static String extractActivityName(final String key) {
        return splitKey(key)[1];
    }

    private static Optional<String> extractAliasType(final String key) {
        final String[] parts = splitKey(key);
        if (parts.length > 2) {
            return Optional.of(parts[2]);
        }
        return Optional.empty();
    }

    private static String[] splitKey(final String key) {
        return key.split("\\*");
    }

}
