package uk.co.idv.domain.entities.lockout.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.DefaultLockoutRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLockoutLevelTest {

    private static final String CHANNEL_ID = "channel-id";
    private static final String ACTIVITY_NAME = "activity-name";

    @Test
    void shouldReturnChannelId() {
        LockoutLevel level = DefaultLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .build();

        assertThat(level.getChannelId()).isEqualTo(CHANNEL_ID);
    }

    @Test
    void shouldReturnActivityName() {
        LockoutLevel level = DefaultLockoutLevel.builder()
                .activityName(ACTIVITY_NAME)
                .build();

        assertThat(level.getActivityName()).isEqualTo(ACTIVITY_NAME);
    }

    @Test
    void shouldNotIncludeAlias() {
        LockoutLevel level = DefaultLockoutLevel.builder().build();

        boolean includesAlias = level.includesAlias();

        assertThat(includesAlias).isFalse();
    }

    @Test
    void shouldNotApplyToRequestWithDifferentChannelId() {
        LockoutLevel level = DefaultLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .build();

        LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId("different-channel")
                .activityName(ACTIVITY_NAME)
                .build();

        boolean applies = level.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldNotApplyToRequestWithDifferentActivityName() {
        LockoutLevel level = DefaultLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .build();

        LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CHANNEL_ID)
                .activityName("different-activity")
                .build();

        boolean applies = level.appliesTo(request);

        assertThat(applies).isFalse();
    }

    @Test
    void shouldApplyToRequestWithSameChannelIdAndActivityName() {
        LockoutLevel level = DefaultLockoutLevel.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .build();

        LockoutRequest request = DefaultLockoutRequest.builder()
                .channelId(CHANNEL_ID)
                .activityName(ACTIVITY_NAME)
                .build();

        boolean applies = level.appliesTo(request);

        assertThat(applies).isTrue();
    }



}