package uk.co.idv.domain.usecases.verification.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.verification.onetimepasscode.CreateOneTimePasscodeVerificationRequest.CreateOneTimePasscodeVerificationRequestBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CreateOneTimePasscodeVerificationRequestTest {

    private final CreateOneTimePasscodeVerificationRequestBuilder builder = CreateOneTimePasscodeVerificationRequest.builder();

    @Test
    void shouldReturnContextId() {
        final UUID contextId = UUID.randomUUID();

        final CreateOneTimePasscodeVerificationRequest request = builder.contextId(contextId).build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnProvidedAlias() {
        final UUID deliveryMethodId = UUID.randomUUID();

        final CreateOneTimePasscodeVerificationRequest request = builder.deliveryMethodId(deliveryMethodId).build();

        assertThat(request.getDeliveryMethodId()).isEqualTo(deliveryMethodId);
    }

}
