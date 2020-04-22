package uk.co.idv.domain.entities.mobiledevice;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class MobileDeviceTest {

    @Test
    void shouldReturnId() {
        final String id = "my-id";

        final MobileDevice device = MobileDevice.builder()
                .id(id)
                .build();

        assertThat(device.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnLastLogin() {
        final Instant lastLogin = Instant.now();

        final MobileDevice device = MobileDevice.builder()
                .lastLogin(lastLogin)
                .build();

        assertThat(device.getLastLogin()).isEqualTo(lastLogin);
    }

    @Test
    void shouldReturnTrusted() {
        final boolean trusted = true;

        final MobileDevice device = MobileDevice.builder()
                .trusted(trusted)
                .build();

        assertThat(device.isTrusted()).isEqualTo(trusted);
    }

    @Test
    void shouldUsedSinceTrueIfLastLoginIsAfterCutoff() {
        final Instant cutoff = Instant.now();
        final Instant lastLogin = cutoff.plusSeconds(1);

        final MobileDevice device = MobileDevice.builder()
                .lastLogin(lastLogin)
                .build();

        assertThat(device.usedSince(cutoff)).isTrue();
    }

    @Test
    void shouldUsedSinceFalseIfLastLoginIsBeforeCutoff() {
        final Instant cutoff = Instant.now();
        final Instant lastLogin = cutoff.minusSeconds(1);

        final MobileDevice device = MobileDevice.builder()
                .lastLogin(lastLogin)
                .build();

        assertThat(device.usedSince(cutoff)).isFalse();
    }

    @Test
    void shouldUsedSinceFalseIfLastLoginIsEqualToCutoff() {
        final Instant cutoff = Instant.now();

        final MobileDevice device = MobileDevice.builder()
                .lastLogin(cutoff)
                .build();

        assertThat(device.usedSince(cutoff)).isFalse();
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(MobileDevice.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
