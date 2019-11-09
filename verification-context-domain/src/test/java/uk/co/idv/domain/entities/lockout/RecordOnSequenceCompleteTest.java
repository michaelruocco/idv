package uk.co.idv.domain.entities.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.lockout.RecordAttemptRequest;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RecordOnSequenceCompleteTest {

    private final VerificationContext context = mock(VerificationContext.class);
    private final VerificationResult result = new FakeVerificationResultSuccessful("method-name");

    private final RecordAttemptStrategy strategy = new RecordOnSequenceComplete();

    @Test
    void shouldReturnType() {
        assertThat(strategy.getType()).isEqualTo(RecordOnSequenceComplete.TYPE);
    }

    @Test
    void shouldReturnFalseIfContextDoesNotContainCompleteSequenceContainingResultMethod() {
        given(context.containsCompleteSequenceContainingMethod(result.getMethodName())).willReturn(false);
        final RecordAttemptRequest request = RecordAttemptRequest.builder()
                .context(context)
                .result(result)
                .build();

        boolean result = strategy.shouldRecordAttempt(request);

        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueIfContextContainsCompleteSequenceContainingResultMethod() {
        given(context.containsCompleteSequenceContainingMethod(result.getMethodName())).willReturn(true);
        final RecordAttemptRequest request = RecordAttemptRequest.builder()
                .context(context)
                .result(result)
                .build();

        boolean result = strategy.shouldRecordAttempt(request);

        assertThat(result).isTrue();
    }

}
