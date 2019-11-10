package uk.co.idv.domain.entities.lockout.policy.recordattempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RecordOnMethodCompleteTest {

    private final VerificationContext context = mock(VerificationContext.class);
    private final VerificationResult result = new FakeVerificationResultSuccessful("method-name");

    private final RecordAttemptStrategy strategy = new RecordOnMethodComplete();

    @Test
    void shouldReturnType() {
        assertThat(strategy.getType()).isEqualTo(RecordOnMethodComplete.TYPE);
    }

    @Test
    void shouldReturnFalseIfContextDoesNotContainCompleteMethod() {
        given(context.containsCompleteMethod(result.getMethodName())).willReturn(false);
        final RecordAttemptRequest request = RecordAttemptRequest.builder()
                .context(context)
                .result(result)
                .build();

        boolean result = strategy.shouldRecordAttempt(request);

        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueIfContextContainsCompleteMethod() {
        given(context.containsCompleteMethod(result.getMethodName())).willReturn(true);
        final RecordAttemptRequest request = RecordAttemptRequest.builder()
                .context(context)
                .result(result)
                .build();

        boolean result = strategy.shouldRecordAttempt(request);

        assertThat(result).isTrue();
    }

}
