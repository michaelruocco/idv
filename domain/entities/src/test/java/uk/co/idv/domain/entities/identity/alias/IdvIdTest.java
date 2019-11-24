package uk.co.idv.domain.entities.identity.alias;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class IdvIdTest {

    @Test
    void shouldThrowExceptionIfValueIsNotValidUuid() {
        final String value = "not-valid-uuid";

        final Throwable error = catchThrowable(() -> new IdvId(value));

        assertThat(error)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("Invalid UUID string: %s", value));
    }

    @Test
    void shouldTakeValidUuidStringAsConstructorArgument() {
        final String value = UUID.randomUUID().toString();

        final Alias alias = new IdvId(value);

        assertThat(alias.getValue()).isEqualTo(value);
    }

    @Test
    void shouldReturnType() {
        final Alias alias = new IdvId(UUID.randomUUID());

        final String type = alias.getType();

        assertThat(type).isEqualTo("idv-id");
    }

    @Test
    void isNotCardNumber() {
        final Alias alias = new IdvId(UUID.randomUUID());

        final boolean cardNumber = alias.isCardNumber();

        assertThat(cardNumber).isFalse();
    }

    @Test
    void shouldReturnValue() {
        final UUID value = UUID.randomUUID();

        final Alias alias = new IdvId(value);

        assertThat(alias.getValue()).isEqualTo(value.toString());
    }

    @Test
    void shouldReturnValueAsUuid() {
        final UUID value = UUID.randomUUID();

        final IdvId alias = new IdvId(value);

        assertThat(alias.getValueAsUuid()).isEqualTo(value);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(IdvId.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .withNonnullFields("value")
                .verify();
    }

}
