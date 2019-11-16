package uk.co.idv.domain.entities.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.DefaultLockoutRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLockoutLevelTest {

    private static final String CHANNEL_ID = "channel-id";
    private static final String ACTIVITY_NAME = "activity-name";

    @Test
    void shouldReturnType() {
        final LockoutLevel level = DefaultLockoutLevel.builder().build();

        final String type = level.getType();

        assertThat(type).isEqualTo("channel-and-activity");
    }

    @Test
    void shouldNotIncludeAlias() {
        final LockoutLevel level = DefaultLockoutLevel.builder().build();

        final boolean includesAlias = level.isAliasLevel();

        assertThat(includesAlias).isFalse();
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
                .activityName(ACTIVITY_NAME)
                .build();

        assertThat(level.getActivityName()).isEqualTo(ACTIVITY_NAME);
    }

    @Test
    void shouldNotApplyToRequestWithDifferentChannelId() {
        final LockoutLevel level = DefaultLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .build();

        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId("different-channel")
                .activityName(ACTIVITY_NAME)
                .build();

        final boolean applies = level.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyToRequestWithDifferentActivityName() {
        final LockoutLevel level = DefaultLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .build();

        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CHANNEL_ID)
                .activityName("different-activity")
                .build();

        final boolean applies = level.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldApplyToRequestWithSameChannelIdAndActivityName() {
        final LockoutLevel level = DefaultLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .build();

        final LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .build();

        final boolean applies = level.appliesTo(request);

        assertThat(applies).isTrue();
    }

}
