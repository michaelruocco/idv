package uk.co.idv.domain.usecases.verification.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.verification.onetimepasscode.GenerateOneTimePasscodeVerificationRequest.GenerateOneTimePasscodeVerificationRequestBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GenerateOneTimePasscodeVerificationRequestTest {

    private final GenerateOneTimePasscodeVerificationRequestBuilder builder = GenerateOneTimePasscodeVerificationRequest.builder();

    @Test
    void shouldReturnContextId() {
        final UUID contextId = UUID.randomUUID();

        final GenerateOneTimePasscodeVerificationRequest request = builder.contextId(contextId).build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnProvidedAlias() {
        final UUID deliveryMethodId = UUID.randomUUID();

        final GenerateOneTimePasscodeVerificationRequest request = builder.deliveryMethodId(deliveryMethodId).build();

        assertThat(request.getDeliveryMethodId()).isEqualTo(deliveryMethodId);
    }

}
