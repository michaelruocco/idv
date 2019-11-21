package uk.co.idv.domain.entities.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.lockout.DefaultLockoutRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLockoutLevelTest {

    private static final String CHANNEL_ID = "channel-id";
    private static final String ACTIVITY_NAME = "activity-name";
    private static final Collection<String> ACTIVITY_NAMES = Collections.singleton(ACTIVITY_NAME);
    private static final Alias ALIAS = AliasesMother.creditCardNumber();
    private static final Collection<String> ALIAS_TYPES = Collections.singleton(ALIAS.getType());

    @Test
    void shouldNotBeAliasLevelIfAliasTypesIsEmpty() {
        final LockoutLevel level = DefaultLockoutLevel.builder().build();

        final boolean includesAlias = level.isAliasLevel();

        assertThat(includesAlias).isFalse();
    }

    @Test
    void shouldNotBeAliasLevelIfAliasTypesContainsAll() {
        final LockoutLevel level = DefaultLockoutLevel.builder()
                .aliasTypes(Collections.singleton(LockoutLevel.ALL))
                .build();

        final boolean includesAlias = level.isAliasLevel();

        assertThat(includesAlias).isFalse();
    }

    @Test
    void shouldNotBeAliasLevelIfAliasTypeIsSet() {
        final LockoutLevel level = DefaultLockoutLevel.builder()
                .aliasTypes(Collections.singleton("alias-type"))
                .build();

        final boolean includesAlias = level.isAliasLevel();

        assertThat(includesAlias).isTrue();
    }

    @Test
    void shouldReturnChannelId() {
        final LockoutLevel level = DefaultLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .build();

        assertThat(level.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    void shouldReturnActivityName() {
        final LockoutLevel level = DefaultLockoutLevel.builder()
                .activityNames(ACTIVITY_NAMES)
                .build();

        assertThat(level.getActivityNames()).isEqualTo(ACTIVITY_NAMES);
    }

    @Test
    void shouldReturnAliasType() {
        final LockoutLevel level = DefaultLockoutLevel.builder()
                .aliasTypes(ALIAS_TYPES)
                .build();

        assertThat(level.getAliasTypes()).containsExactlyElementsOf(ALIAS_TYPES);
    }

    @Test
    void shouldNotApplyToRequestWithDifferentChannelId() {
        final LockoutLevel level = DefaultLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityNames(ACTIVITY_NAMES)
                .aliasTypes(ALIAS_TYPES)
                .build();

        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId("different-channel")
                .activityName(ACTIVITY_NAME)
                .alias(ALIAS)
                .build();

        final boolean applies = level.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyToRequestWithDifferentActivityName() {
        final LockoutLevel level = DefaultLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityNames(ACTIVITY_NAMES)
                .aliasTypes(ALIAS_TYPES)
                .build();

        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CHANNEL_ID)
                .activityName("different-activity")
                .alias(ALIAS)
                .build();

        final boolean applies = level.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyToRequestWithDifferentAliasType() {
        final LockoutLevel level = DefaultLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityNames(ACTIVITY_NAMES)
                .aliasTypes(Collections.singleton("different-alias-type"))
                .build();

        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .alias(ALIAS)
                .build();

        final boolean applies = level.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldApplyToRequestWithSameChannelIdAndActivityName() {
        final LockoutLevel level = DefaultLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityNames(ACTIVITY_NAMES)
                .aliasTypes(ALIAS_TYPES)
                .build();

        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .alias(ALIAS)
                .build();

        final boolean applies = level.appliesTo(request);

        assertThat(applies).isTrue();
    }

    @Test
    void shouldApplyToRequestWithSameChannelIdAndAllActivityNamesAndAliasTypes() {
        final LockoutLevel level = DefaultLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityNames(Collections.singleton(LockoutLevel.ALL))
                .aliasTypes(Collections.singleton(LockoutLevel.ALL))
                .build();

        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .alias(ALIAS)
                .build();

        final boolean applies = level.appliesTo(request);

        assertThat(applies).isTrue();
    }

}
