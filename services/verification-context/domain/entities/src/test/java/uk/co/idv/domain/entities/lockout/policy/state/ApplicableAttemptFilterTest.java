package uk.co.idv.domain.entities.lockout.policy.state;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.policy.PolicyLevel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ApplicableAttemptFilterTest {

    private final PolicyLevel level = mock(PolicyLevel.class);

    private final ApplicableAttemptFilter filter = new ApplicableAttemptFilter(level);

    @Test
    void shouldFilterByPolicyLevelForDefaultPolicyLevel() {
        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequestMother.withAttempts(attempts);
        given(level.isAliasLevel()).willReturn(false);
        final VerificationAttempts expectedAttempts = mock(VerificationAttempts.class);
        given(attempts.filterApplicable(level)).willReturn(expectedAttempts);

        final VerificationAttempts filteredAttempts = filter.filter(request);

        assertThat(filteredAttempts).isEqualTo(expectedAttempts);
    }

    @Test
    void shouldFilterByAliasAndThenPolicyLevelForAliasPolicyLevel() {
        final VerificationAttempts attempts = mock(VerificationAttempts.class);
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequestMother.withAttempts(attempts);
        given(level.isAliasLevel()).willReturn(true);
        final VerificationAttempts aliasFilteredAttempts = mock(VerificationAttempts.class);
        given(attempts.filterApplicable(request.getAlias())).willReturn(aliasFilteredAttempts);
        final VerificationAttempts expectedAttempts = mock(VerificationAttempts.class);
        given(aliasFilteredAttempts.filterApplicable(level)).willReturn(expectedAttempts);

        final VerificationAttempts filteredAttempts = filter.filter(request);

        assertThat(filteredAttempts).isEqualTo(expectedAttempts);
    }

}
