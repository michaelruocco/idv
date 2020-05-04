package uk.co.idv.domain.entities.verificationcontext.method;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod.CannotAddResultToIneligibleMethodException;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod.CannotAddResultToMethodException;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod.MethodAlreadyCompleteException;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DefaultVerificationMethodTest {

    private static final String NAME = "my-method-name";
    private final VerificationMethodParams params = mock(VerificationMethodParams.class);
    private final Eligibility eligibility = mock(Eligibility.class);
    private final VerificationResults results = mock(VerificationResults.class);

    private final VerificationMethod method = new DefaultVerificationMethod(NAME, params, eligibility, results);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(NAME);
    }

    @Test
    void shouldReturnParams() {
        assertThat(method.getParams()).isEqualTo(params);
    }

    @Test
    void shouldReturnMaxAttempts() {
        final int expectedMaxAttempts = 5;
        given(params.getMaxAttempts()).willReturn(expectedMaxAttempts);

        final int maxAttempts = method.getMaxAttempts();

        assertThat(maxAttempts).isEqualTo(expectedMaxAttempts);
    }

    @Test
    void shouldReturnDuration() {
        final Duration expectedDuration = Duration.ofMinutes(5);
        given(params.getDuration()).willReturn(expectedDuration);

        final Duration duration = method.getDuration();

        assertThat(duration).isEqualTo(expectedDuration);
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(eligibility);
    }

    @Test
    void shouldReturnIsEligibleFromEligibility() {
        final boolean expectedEligible = true;
        given(eligibility.isEligible()).willReturn(expectedEligible);

        final boolean eligible = method.isEligible();

        assertThat(eligible).isEqualTo(expectedEligible);
    }

    @Test
    void shouldReturnEligibilityReasonFromEligibility() {
        final Optional<String> expectedReason = Optional.of("my-reason");
        given(eligibility.getReason()).willReturn(expectedReason);

        final Optional<String> reason = method.getEligibilityReason();

        assertThat(reason).isEqualTo(expectedReason);
    }

    @Test
    void shouldHaveName() {
        assertThat(method.hasName(NAME)).isTrue();
    }

    @Test
    void shouldNotHaveAnyOtherName() {
        assertThat(method.hasName("other")).isFalse();
    }

    @Test
    void shouldReturnHasResultsIfResultsAreEmpty() {
        given(results.isEmpty()).willReturn(false);

        assertThat(method.hasResults()).isTrue();
    }

    @Test
    void shouldNotReturnHasResultsIfResultsAreEmpty() {
        given(results.isEmpty()).willReturn(true);

        assertThat(method.hasResults()).isFalse();
    }

    @Test
    void shouldNotBeCompleteIfMethodIsIneligible() {
        given(eligibility.isEligible()).willReturn(false);

        assertThat(method.isComplete()).isFalse();
    }

    @Test
    void shouldBeCompleteIfHasSuccessfulResult() {
        given(results.containsSuccessful()).willReturn(true);

        assertThat(method.isSuccessful()).isTrue();
    }

    @Test
    void shouldNotBeCompleteIfHasNumberOfResultsLessThanMaxAttempts() {
        given(eligibility.isEligible()).willReturn(true);
        given(results.containsSuccessful()).willReturn(false);
        given(params.getMaxAttempts()).willReturn(3);
        given(results.size()).willReturn(2);

        assertThat(method.isComplete()).isFalse();
    }

    @Test
    void shouldBeCompleteIfHasNumberOfResultsEqualToMaxAttempts() {
        final int maxAttempts = 3;
        given(eligibility.isEligible()).willReturn(true);
        given(results.containsSuccessful()).willReturn(false);
        given(params.getMaxAttempts()).willReturn(maxAttempts);
        given(results.size()).willReturn(maxAttempts);

        assertThat(method.isComplete()).isTrue();
    }

    @Test
    void shouldBeCompleteIfHasNumberOfResultsGreaterThanMaxAttempts() {
        given(eligibility.isEligible()).willReturn(true);
        given(results.containsSuccessful()).willReturn(false);
        given(params.getMaxAttempts()).willReturn(3);
        given(results.size()).willReturn(4);

        assertThat(method.isComplete()).isTrue();
    }

    @Test
    void isSuccessfulShouldReturnResultsContainsSuccessful() {
        given(results.containsSuccessful()).willReturn(true);

        assertThat(method.isSuccessful()).isTrue();
    }

    @Test
    void shouldNotAddResultIfIneligible() {
        final VerificationResult result = mock(VerificationResult.class);
        given(eligibility.isEligible()).willReturn(false);

        final Throwable error = catchThrowable(() -> method.addResult(result));

        assertThat(error)
                .isInstanceOf(CannotAddResultToIneligibleMethodException.class)
                .hasMessage(NAME);
    }

    @Test
    void shouldNotAddResultIfResultMethodNameDoesNotMatchMethodName() {
        final VerificationResult result = mock(VerificationResult.class);
        given(result.getMethodName()).willReturn("other-method-name");
        given(eligibility.isEligible()).willReturn(true);

        final Throwable error = catchThrowable(() -> method.addResult(result));

        assertThat(error)
                .isInstanceOf(CannotAddResultToMethodException.class)
                .hasMessage("cannot add result for method other-method-name to method my-method-name");
    }

    @Test
    void shouldNotAddResultIfMethodIsAlreadyComplete() {
        final VerificationResult result = mock(VerificationResult.class);
        given(result.getMethodName()).willReturn(NAME);
        given(eligibility.isEligible()).willReturn(true);
        given(results.containsSuccessful()).willReturn(true);

        final Throwable error = catchThrowable(() -> method.addResult(result));

        assertThat(error)
                .isInstanceOf(MethodAlreadyCompleteException.class)
                .hasMessage(NAME);
    }

    @Test
    void shouldAddResultIfMethodIsEligibleNotCompleteAndResultNameMatchesMethodName() {
        final VerificationResult result = mock(VerificationResult.class);
        given(result.getMethodName()).willReturn(NAME);
        given(eligibility.isEligible()).willReturn(true);
        given(params.getMaxAttempts()).willReturn(5);

        method.addResult(result);

        verify(results).add(result);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(DefaultVerificationMethod.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
