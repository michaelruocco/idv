package uk.co.idv.domain.entities.phonenumber;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OtherPhoneNumberTest {

    @Test
    void shouldReturnType() {
        final PhoneNumber number = OtherPhoneNumber.builder().build();

        assertThat(number.getType()).isEqualTo(OtherPhoneNumber.TYPE);
    }

    @Test
    void shouldReturnId() {
        final UUID id = UUID.randomUUID();

        final PhoneNumber number = OtherPhoneNumber.builder()
                .id(id)
                .build();

        assertThat(number.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnValue() {
        final String value = "+447089123456";

        final PhoneNumber number = OtherPhoneNumber.builder()
                .value(value)
                .build();

        assertThat(number.getValue()).isEqualTo(value);
    }

    @Test
    void shouldReturnIsMobileFalse() {
        final PhoneNumber number = OtherPhoneNumber.builder().build();

        assertThat(number.isMobile()).isFalse();
    }

}
