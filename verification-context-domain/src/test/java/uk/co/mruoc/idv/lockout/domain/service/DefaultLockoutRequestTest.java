package uk.co.mruoc.idv.lockout.domain.service;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.entities.identity.AliasesMother;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLockoutRequest.DefaultLockoutRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLockoutRequestTest {

    private final DefaultLockoutRequestBuilder builder = DefaultLockoutRequest.builder();

    @Test
    void shouldReturnChannelId() {
        final String channelId = "channel-id";

        final LockoutRequest request = builder.channelId(channelId).build();

        assertThat(request.getChannelId()).isEqualTo(channelId);
    }

    @Test
    void shouldReturnActivityName() {
        final String activityName = "activity-name";

        final LockoutRequest request = builder.activityName(activityName).build();

        assertThat(request.getActivityName()).isEqualTo(activityName);
    }

    @Test
    void shouldReturnAlias() {
        final Alias alias = AliasesMother.creditCardNumber();

        final LockoutRequest request = builder.alias(alias).build();

        assertThat(request.getAlias()).isEqualTo(alias);
    }

    @Test
    void shouldReturnAliasType() {
        final Alias alias = AliasesMother.creditCardNumber();

        final LockoutRequest request = builder.alias(alias).build();

        assertThat(request.getAliasType()).isEqualTo(alias.getType());
    }

    @Test
    void shouldBeEqualIfAllValuesAreTheSame() {
        EqualsVerifier.forClass(DefaultLockoutRequest.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
