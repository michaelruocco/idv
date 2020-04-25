package uk.co.idv.domain.usecases.verificationcontext.result;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.usecases.verificationcontext.result.RecordResultRequest.RecordResultRequestBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class RecordResultRequestTest {

    private final RecordResultRequestBuilder builder = RecordResultRequest.builder();

    @Test
    void shouldReturnContextId() {
        final UUID contextId = UUID.randomUUID();

        final RecordResultRequest request = builder.contextId(contextId).build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnResult() {
        final VerificationResult result = mock(VerificationResult.class);

        final RecordResultRequest request = builder.result(result).build();

        assertThat(request.getResult()).isEqualTo(result);
    }

}
