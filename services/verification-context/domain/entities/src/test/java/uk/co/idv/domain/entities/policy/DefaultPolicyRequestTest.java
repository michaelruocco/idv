package uk.co.idv.domain.entities.policy;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.policy.DefaultPolicyRequest.DefaultPolicyRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultPolicyRequestTest {

    private final DefaultPolicyRequestBuilder builder = DefaultPolicyRequest.builder();

    @Test
    void shouldReturnChannelId() {
        final String channelId = "channel-id";

        final PolicyRequest request = builder.channelId(channelId).build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityName() {
        final String activityName = "activity-name";

        final PolicyRequest request = builder.activityName(activityName).build();

        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldReturnAliasType() {
        final String aliasType = "alias-type";

        final PolicyRequest request = builder.aliasType(aliasType).build();

        assertThat(request.getAliasType()).isEqualTo(aliasType);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(DefaultPolicyRequest.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
