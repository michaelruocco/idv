package uk.co.idv.domain.entities.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.lockout.DefaultLockoutRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

import static org.assertj.core.api.Assertions.assertThat;

class AliasLockoutLevelTest {

    private static final String CHANNEL_ID = "channel-id";
    private static final String ACTIVITY_NAME = "activity-name";
    private static final Alias ALIAS = AliasesMother.creditCardNumber();

    @Test
    void shouldReturnType() {
        final LockoutLevel level = AliasLockoutLevel.builder().build();

        final String type = level.getType();

        assertThat(type).isEqualTo("channel-activity-and-alias");
    }

    @Test
    void shouldIncludeAlias() {
        final LockoutLevel level = AliasLockoutLevel.builder().build();

        final boolean includesAlias = level.isAliasLevel();

        assertThat(includesAlias).isTrue();
    }

    @Test
    void shouldReturnChannelId() {
        final LockoutLevel level = AliasLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .build();

        assertThat(level.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    void shouldReturnActivityName() {
        final LockoutLevel level = AliasLockoutLevel.builder()
                .activityName(ACTIVITY_NAME)
                .build();

        assertThat(level.getActivityName()).isEqualTo(ACTIVITY_NAME);
    }

    @Test
    void shouldReturnAliasType() {
        final AliasLockoutLevel level = AliasLockoutLevel.builder()
                .aliasType(ALIAS.getType())
                .build();

        assertThat(level.getAliasType()).isEqualTo(ALIAS.getType());
    }

    @Test
    void shouldNotApplyToRequestWithDifferentChannelId() {
        final LockoutLevel level = AliasLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .aliasType(ALIAS.getType())
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
        final LockoutLevel level = AliasLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .aliasType(ALIAS.getType())
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
        final LockoutLevel level = AliasLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .aliasType("different-alias-type")
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
        final LockoutLevel level = AliasLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .aliasType(ALIAS.getType())
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
