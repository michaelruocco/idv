package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SmsDeliveryMethodTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NUMBER = "07809376680";

    @Test
    void shouldReturnId() {
        final SmsDeliveryMethod mobileNumber = new SmsDeliveryMethod(ID, NUMBER);

        assertThat(mobileNumber.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnNumberValue() {
        final SmsDeliveryMethod mobileNumber = new SmsDeliveryMethod(ID, NUMBER);

        assertThat(mobileNumber.getValue()).isEqualTo(NUMBER);
    }

    @Test
    void shouldPopulateRandomIdIfNotProvided() {
        final SmsDeliveryMethod mobileNumber = new SmsDeliveryMethod(NUMBER);

        assertThat(mobileNumber.getId()).isNotNull();
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(SmsDeliveryMethod.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
