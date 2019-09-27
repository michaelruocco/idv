package uk.co.mruoc.idv.lockout.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.lockout.domain.service.RecordAttemptRequest.RecordAttemptRequestBuilder;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class RecordAttemptRequestTest {

    private final RecordAttemptRequestBuilder builder = RecordAttemptRequest.builder();

    @Test
    void shouldReturnResult() {
        final VerificationResult result = mock(VerificationResult.class);

        final RecordAttemptRequest request = builder.result(result).build();

        assertThat(request.getResult()).isEqualTo(result);
    }

    @Test
    void shouldReturnContext() {
        final VerificationContext context = mock(VerificationContext.class);

        final RecordAttemptRequest request = builder.context(context).build();

        assertThat(request.getContext()).isEqualTo(context);
    }

}
