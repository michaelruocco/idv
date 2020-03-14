package uk.co.idv.domain.usecases.verification.onetimepasscode;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.verification.onetimepasscode.SendOneTimePasscodeRequest.SendOneTimePasscodeRequestBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SendOneTimePasscodeRequestTest {

    private final SendOneTimePasscodeRequestBuilder builder = SendOneTimePasscodeRequest.builder();

    @Test
    void shouldReturnContextId() {
        final UUID contextId = UUID.randomUUID();

        final SendOneTimePasscodeRequest request = builder.contextId(contextId).build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnProvidedAlias() {
        final UUID deliveryMethodId = UUID.randomUUID();

        final SendOneTimePasscodeRequest request = builder.deliveryMethodId(deliveryMethodId).build();

        assertThat(request.getDeliveryMethodId()).isEqualTo(deliveryMethodId);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(SendOneTimePasscodeRequest.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
