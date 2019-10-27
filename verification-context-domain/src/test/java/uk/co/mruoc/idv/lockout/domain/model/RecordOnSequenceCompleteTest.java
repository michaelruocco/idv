package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.service.RecordAttemptRequest;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RecordOnSequenceCompleteTest {

    private final VerificationContext context = mock(VerificationContext.class);
    private final VerificationResult result = new FakeVerificationResultSuccessful("method-name");

    private final RecordAttemptStrategy strategy = new RecordOnSequenceComplete();

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
