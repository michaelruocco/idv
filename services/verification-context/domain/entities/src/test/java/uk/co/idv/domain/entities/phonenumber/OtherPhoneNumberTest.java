package uk.co.idv.domain.entities.phonenumber;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OtherPhoneNumberTest {

    private static final String VALUE = "+447089123456";

    @Test
    void shouldReturnType() {
        final PhoneNumber number = new OtherPhoneNumber(VALUE);

        assertThat(number.getType()).isEqualTo(PhoneNumberType.OTHER);
    }

    @Test
    void shouldReturnValue() {
        final PhoneNumber number = new OtherPhoneNumber(VALUE);

        assertThat(number.getValue()).isEqualTo(VALUE);
    }

    @Test
    void shouldReturnIsMobileFalse() {
        final PhoneNumber number = new OtherPhoneNumber(VALUE);

        assertThat(number.isMobile()).isFalse();
    }

}
