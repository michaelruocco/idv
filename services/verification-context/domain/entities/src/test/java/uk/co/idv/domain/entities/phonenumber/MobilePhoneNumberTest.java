package uk.co.idv.domain.entities.phonenumber;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MobilePhoneNumberTest {

    @Test
    void shouldReturnType() {
        final PhoneNumber number = MobilePhoneNumber.builder().build();

        assertThat(number.getType()).isEqualTo(MobilePhoneNumber.TYPE);
    }

    @Test
    void shouldReturnId() {
        final UUID id = UUID.randomUUID();

        final PhoneNumber number = MobilePhoneNumber.builder()
                .id(id)
                .build();

        assertThat(number.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnValue() {
        final String value = "+447089123456";
        final PhoneNumber number = MobilePhoneNumber.builder()
                .value(value)
                .build();

        assertThat(number.getValue()).isEqualTo(value);
    }

    @Test
    void shouldReturnIsMobileTrue() {
        final PhoneNumber number = MobilePhoneNumber.builder().build();

        assertThat(number.isMobile()).isTrue();
    }

}
