package uk.co.idv.domain.entities.verification.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethodMother;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeDeliveryTest {

    @Test
    void shouldReturnDeliveryMethod() {
        final DeliveryMethod method = DeliveryMethodMother.sms();

        final OneTimePasscodeDelivery delivery = OneTimePasscodeDelivery.builder()
                .method(method)
                .build();

        assertThat(delivery.getMethod()).isEqualTo(method);
    }

    @Test
    void shouldReturnPasscode() {
        final String passcode = "12345678";

        final OneTimePasscodeDelivery delivery = OneTimePasscodeDelivery.builder()
                .passcode(passcode)
                .build();

        assertThat(delivery.getPasscode()).isEqualTo(passcode);
    }

    @Test
    void shouldReturnActivity() {
        final Activity activity = ActivityMother.login();

        final OneTimePasscodeDelivery delivery = OneTimePasscodeDelivery.builder()
                .activity(activity)
                .build();

        assertThat(delivery.getActivity()).isEqualTo(activity);
    }

    @Test
    void shouldReturnHasPasscodeTrueIfProvidedPasscodeDoesNotMatchDeliveryPasscode() {
        final String passcode = "12345678";

        final OneTimePasscodeDelivery delivery = OneTimePasscodeDelivery.builder()
                .passcode(passcode)
                .build();

        assertThat(delivery.hasPasscode("11111111")).isFalse();
    }

    @Test
    void shouldReturnHasPasscodeTrueIfProvidedPasscodeMatchesDeliveryPasscode() {
        final String passcode = "12345678";

        final OneTimePasscodeDelivery delivery = OneTimePasscodeDelivery.builder()
                .passcode(passcode)
                .build();

        assertThat(delivery.hasPasscode(passcode)).isTrue();
    }

}
