package uk.co.mruoc.idv.lockout.domain.model;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.service.RecordAttemptRequest;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RecordOnMethodCompleteTest {

    private final VerificationContext context = mock(VerificationContext.class);
    private final VerificationResult result = new FakeVerificationResultSuccessful("method-name");

    private final RecordAttemptStrategy strategy = new RecordOnMethodComplete();

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
