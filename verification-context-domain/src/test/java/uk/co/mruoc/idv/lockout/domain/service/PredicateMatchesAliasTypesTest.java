package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PredicateMatchesAliasTypesTest {

    private static final String ALIAS_TYPE = "alias-type";

    @Test
    void shouldReturnTrueIfLockoutPolicyRequestMatchesAliasType() {
        final Predicate<LockoutRequest> predicate = new PredicateMatchesAliasTypes(ALIAS_TYPE);
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getAliasType()).willReturn(ALIAS_TYPE);

        boolean result = predicate.test(request);

        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseIfLockoutPolicyRequestDoesNotMatchAliasType() {
        final Predicate<LockoutRequest> predicate = new PredicateMatchesAliasTypes(ALIAS_TYPE);
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getAliasType()).willReturn("other-alias-type");

        boolean result = predicate.test(request);

        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueForAllAliasTypesIfNoneProvided() {
        final Predicate<LockoutRequest> predicate = new PredicateMatchesAliasTypes();
        final LockoutStateRequest request = mock(LockoutStateRequest.class);
        given(request.getAliasType()).willReturn("any-alias-type");

        boolean result = predicate.test(request);

        assertThat(result).isTrue();
    }

}
