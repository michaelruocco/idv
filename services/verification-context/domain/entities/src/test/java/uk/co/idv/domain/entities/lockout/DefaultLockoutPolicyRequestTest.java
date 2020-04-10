package uk.co.idv.domain.entities.lockout;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.DefaultLockoutPolicyRequest.DefaultLockoutPolicyRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLockoutPolicyRequestTest {

    private final DefaultLockoutPolicyRequestBuilder builder = DefaultLockoutPolicyRequest.builder();

    @Test
    void shouldReturnChannelId() {
        final String channelId = "channel-id";

        final LockoutPolicyRequest request = builder.channelId(channelId).build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityName() {
        final String activityName = "activity-name";

        final LockoutPolicyRequest request = builder.activityName(activityName).build();

        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldReturnAliasType() {
        final String aliasType = "alias-type";

        final LockoutPolicyRequest request = builder.aliasType(aliasType).build();

        assertThat(request.getAliasType()).isEqualTo(aliasType);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(DefaultLockoutPolicyRequest.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
