package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PredicateMatchesActivityNamesTest {

    private static final String ACTIVITY_NAME = "activity-name";

    @Test
    void shouldReturnTrueIfLockoutPolicyRequestMatchesActivityName() {
        final Predicate<LockoutPolicyRequest> predicate = new PredicateMatchesActivityNames(ACTIVITY_NAME);
        final LockoutPolicyRequest request = mock(LockoutPolicyRequest.class);
        given(request.getActivityName()).willReturn(ACTIVITY_NAME);

        boolean result = predicate.test(request);

        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseIfLockoutPolicyRequestDoesNotMatchActivityName() {
        final Predicate<LockoutPolicyRequest> predicate = new PredicateMatchesActivityNames(ACTIVITY_NAME);
        final LockoutPolicyRequest request = mock(LockoutPolicyRequest.class);
        given(request.getActivityName()).willReturn("other-activity-name");

        boolean result = predicate.test(request);

        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueForAllActivityNamesIfNoneProvided() {
        final Predicate<LockoutPolicyRequest> predicate = new PredicateMatchesActivityNames();
        final LockoutPolicyRequest request = mock(LockoutPolicyRequest.class);
        given(request.getChannelId()).willReturn("any-activity-name");

        boolean result = predicate.test(request);

        assertThat(result).isTrue();
    }

}
