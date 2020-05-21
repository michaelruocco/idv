package uk.co.idv.domain.usecases.onetimepasscode.send;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.onetimepasscode.send.ResendOneTimePasscodeRequest.ResendOneTimePasscodeRequestBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ResendOneTimePasscodeRequestTest {

    private final ResendOneTimePasscodeRequestBuilder builder = ResendOneTimePasscodeRequest.builder();

    @Test
    void shouldReturnId() {
        final UUID id = UUID.randomUUID();

        final ResendOneTimePasscodeRequest request = builder.id(id).build();

        assertThat(request.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnContextId() {
        final UUID contextId = UUID.randomUUID();

        final ResendOneTimePasscodeRequest request = builder.contextId(contextId).build();

        assertThat(request.getContextId()).isEqualTo(contextId);
    }

    @Test
    void shouldReturnProvidedAlias() {
        final UUID deliveryMethodId = UUID.randomUUID();

        final ResendOneTimePasscodeRequest request = builder.deliveryMethodId(deliveryMethodId).build();

        assertThat(request.getDeliveryMethodId()).isEqualTo(deliveryMethodId);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(ResendOneTimePasscodeRequest.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
