package uk.co.idv.domain.entities.phonenumber;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneNumbersTest {

    private final PhoneNumber mobileNumber = PhoneNumberMother.mobile();
    private final PhoneNumber otherNumber = PhoneNumberMother.other();

    private final PhoneNumbers numbers = new PhoneNumbers(mobileNumber, otherNumber);

    @Test
    void shouldBeIterable() {
        assertThat(numbers).containsExactly(mobileNumber, otherNumber);
    }

    @Test
    void shouldReturnMobileNumbers() {
        assertThat(numbers.getMobileNumbers()).containsExactly(mobileNumber);
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(PhoneNumber.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
