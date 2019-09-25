package uk.co.mruoc.idv.verificationcontext.domain.service;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.service.UpdateContextResultRequest.UpdateContextResultRequestBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class UpdateContextResultRequestTest {

    private final UpdateContextResultRequestBuilder builder = UpdateContextResultRequest.builder();

    @Test
    void shouldReturnContextId() {
        final UUID contextId = UUID.randomUUID();

        final UpdateContextResultRequest request = builder.contextId(contextId).build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnResult() {
        final VerificationResult result = mock(VerificationResult.class);

        final UpdateContextResultRequest request = builder.result(result).build();

        assertThat(request.getResult()).isEqualTo(result);
    }

}
