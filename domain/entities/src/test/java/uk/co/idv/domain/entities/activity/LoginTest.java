package uk.co.idv.domain.entities.activity;

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

}
