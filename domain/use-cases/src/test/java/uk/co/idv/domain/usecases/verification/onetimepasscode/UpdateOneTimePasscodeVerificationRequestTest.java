package uk.co.idv.domain.usecases.verification.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.verification.onetimepasscode.UpdateOneTimePasscodeVerificationRequest.UpdateOneTimePasscodeVerificationRequestBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateOneTimePasscodeVerificationRequestTest {

    private final UpdateOneTimePasscodeVerificationRequestBuilder builder = UpdateOneTimePasscodeVerificationRequest.builder();

    @Test
    void shouldReturnId() {
        final UUID id = UUID.randomUUID();

        final UpdateOneTimePasscodeVerificationRequest request = builder.id(id).build();

        assertThat(request.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnContextId() {
        final UUID contextId = UUID.randomUUID();

        final UpdateOneTimePasscodeVerificationRequest request = builder.contextId(contextId).build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnProvidedAlias() {
        final UUID deliveryMethodId = UUID.randomUUID();

        final UpdateOneTimePasscodeVerificationRequest request = builder.deliveryMethodId(deliveryMethodId).build();

        assertThat(request.getDeliveryMethodId()).isEqualTo(deliveryMethodId);
    }

}
