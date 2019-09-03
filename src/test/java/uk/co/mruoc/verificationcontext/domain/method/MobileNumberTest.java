package uk.co.mruoc.verificationcontext.domain.method;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MobileNumberTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NUMBER = "07809376680";
    private static final String MASKED = "********680";

    @Test
    void shouldReturnId() {
        final MobileNumber mobileNumber = new MobileNumber(ID, NUMBER);

        assertThat(mobileNumber.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnNumberValue() {
        final MobileNumber mobileNumber = new MobileNumber(ID, NUMBER);

        assertThat(mobileNumber.getNumber()).isEqualTo(NUMBER);
    }

    @Test
    void shouldReturnMaskedValue() {
        final MobileNumber mobileNumber = new MobileNumber(ID, NUMBER);

        assertThat(mobileNumber.getMasked()).isEqualTo(MASKED);
    }

    @Test
    void shouldPopulateRandomIdIfNotProvided() {
        final MobileNumber mobileNumber = new MobileNumber(NUMBER);

        assertThat(mobileNumber.getId()).isNotNull();
    }

}

