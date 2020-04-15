package uk.co.idv.domain.entities.phonenumber;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MobilePhoneNumberTest {

    private static final String VALUE = "+447089123456";

    @Test
    void shouldReturnType() {
        final PhoneNumber number = new MobilePhoneNumber(VALUE);

        assertThat(number.getType()).isEqualTo(PhoneNumberType.MOBILE);
    }

    @Test
    void shouldReturnValue() {
        final PhoneNumber number = new MobilePhoneNumber(VALUE);

        assertThat(number.getValue()).isEqualTo(VALUE);
    }

    @Test
    void shouldReturnIsMobileTrue() {
        final PhoneNumber number = new MobilePhoneNumber(VALUE);

        assertThat(number.isMobile()).isTrue();
    }

}
