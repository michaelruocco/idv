package uk.co.idv.domain.entities.activity;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class LoginTest {

    private static final Instant TIMESTAMP = Instant.now();

    private final Activity login = new Login(TIMESTAMP);

    @Test
    void shouldReturnName() {
        assertThat(login.getName()).isEqualTo(Login.NAME);
    }

    @Test
    void shouldReturnTimestamp() {
        assertThat(login.getTimestamp()).isEqualTo(TIMESTAMP);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(Login.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
