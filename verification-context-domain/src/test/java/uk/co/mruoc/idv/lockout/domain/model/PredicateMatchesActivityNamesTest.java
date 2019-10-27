package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateRequest;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PredicateMatchesActivityNamesTest {

    private static final String ACTIVITY_NAME = "activity-name";

    @Test
    void shouldReturnTrueIfLockoutPolicyRequestMatchesActivityName() {
        final Predicate<LockoutRequest> predicate = new PredicateMatchesActivityNames(ACTIVITY_NAME);
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getActivityName()).willReturn(ACTIVITY_NAME);

        boolean result = predicate.test(request);

        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseIfLockoutPolicyRequestDoesNotMatchActivityName() {
        final Predicate<LockoutRequest> predicate = new PredicateMatchesActivityNames(ACTIVITY_NAME);
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getActivityName()).willReturn("other-activity-name");

        boolean result = predicate.test(request);

        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueForAllActivityNamesIfNoneProvided() {
        final Predicate<LockoutRequest> predicate = new PredicateMatchesActivityNames();
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getChannelId()).willReturn("any-activity-name");

        boolean result = predicate.test(request);

        assertThat(result).isTrue();
    }

}
