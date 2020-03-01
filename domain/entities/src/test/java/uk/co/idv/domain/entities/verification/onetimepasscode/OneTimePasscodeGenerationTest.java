package uk.co.idv.domain.entities.verification.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethodMother;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeGenerationTest {

    @Test
    void shouldReturnDeliveryMethod() {
        final DeliveryMethod deliveryMethod = DeliveryMethodMother.sms();

        final OneTimePasscodeGeneration generation = OneTimePasscodeGeneration.builder()
                .deliveryMethod(deliveryMethod)
                .build();

        assertThat(generation.getDeliveryMethod()).isEqualTo(deliveryMethod);
    }

    @Test
    void shouldReturnPasscode() {
        final String passcode = "12345678";

        final OneTimePasscodeGeneration generation = OneTimePasscodeGeneration.builder()
                .passcode(passcode)
                .build();

        assertThat(generation.getPasscode()).isEqualTo(passcode);
    }

    @Test
    void shouldReturnIsValidFalseIfProvidedPasscodeDoesNotMatchGenerationPasscode() {
        final String passcode = "12345678";

        final OneTimePasscodeGeneration generation = OneTimePasscodeGeneration.builder()
                .passcode(passcode)
                .build();

        assertThat(generation.isValid("11111111")).isFalse();
    }

    @Test
    void shouldReturnIsValidTrueIfProvidedPasscodeMatchesGenerationPasscode() {
        final String passcode = "12345678";

        final OneTimePasscodeGeneration generation = OneTimePasscodeGeneration.builder()
                .passcode(passcode)
                .build();

        assertThat(generation.isValid(passcode)).isTrue();
    }

}
