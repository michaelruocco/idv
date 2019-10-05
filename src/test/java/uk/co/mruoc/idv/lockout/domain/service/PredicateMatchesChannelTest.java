package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PredicateMatchesChannelTest {

    private static final String CHANNEL_ID = "channel-id";

    @Test
    void shouldReturnTrueIfLockoutPolicyRequestMatchesChannelId() {
        final Predicate<LockoutPolicyRequest> predicate = new PredicateMatchesChannel(CHANNEL_ID);
        final LockoutPolicyRequest request = mock(LockoutPolicyRequest.class);
        given(request.getChannelId()).willReturn(CHANNEL_ID);

        boolean result = predicate.test(request);

        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseIfLockoutPolicyRequestDoesNotMatchChannelId() {
        final Predicate<LockoutPolicyRequest> predicate = new PredicateMatchesChannel(CHANNEL_ID);
        final LockoutPolicyRequest request = mock(LockoutPolicyRequest.class);
        given(request.getChannelId()).willReturn("other-channel-id");

        boolean result = predicate.test(request);

        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueForAllChannelIdsIfNoneProvided() {
        final Predicate<LockoutPolicyRequest> predicate = new PredicateMatchesChannel();
        final LockoutPolicyRequest request = mock(LockoutPolicyRequest.class);
        given(request.getChannelId()).willReturn("any-channel-id");

        boolean result = predicate.test(request);

        assertThat(result).isTrue();
    }

}
