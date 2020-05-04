package uk.co.idv.domain.entities.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethodMother;

import java.time.Instant;

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
    void shouldReturnMessage() {
        final String message = "message";

        final OneTimePasscodeDelivery delivery = OneTimePasscodeDelivery.builder()
                .message(message)
                .build();

        assertThat(delivery.getMessage()).isEqualTo(message);
    }

    @Test
    void shouldReturnSentTimestamp() {
        final Instant sent = Instant.now();

        final OneTimePasscodeDelivery delivery = OneTimePasscodeDelivery.builder()
                .sent(sent)
                .build();

        assertThat(delivery.getSent()).isEqualTo(sent);
    }

    @Test
    void shouldReturnExpiryTimestamp() {
        final Instant expiry = Instant.now();

        final OneTimePasscodeDelivery delivery = OneTimePasscodeDelivery.builder()
                .expiry(expiry)
                .build();

        assertThat(delivery.getExpiry()).isEqualTo(expiry);
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

    @Test
    void shouldReturnHasExpiredTrueIfProvidedInstantIsAfterExpiry() {
        final Instant expiry = Instant.now();
        final Instant instant = expiry.plusMillis(1);
        final OneTimePasscodeDelivery delivery = OneTimePasscodeDelivery.builder()
                .expiry(expiry)
                .build();

        assertThat(delivery.hasExpired(instant)).isTrue();
    }

    @Test
    void shouldReturnHasExpiredFalseIfProvidedInstantIsEqualToExpiry() {
        final Instant expiry = Instant.now();
        final OneTimePasscodeDelivery delivery = OneTimePasscodeDelivery.builder()
                .expiry(expiry)
                .build();

        assertThat(delivery.hasExpired(expiry)).isFalse();
    }

    @Test
    void shouldReturnHasExpiredFalseIfProvidedInstantIsBeforeExpiry() {
        final Instant expiry = Instant.now();
        final Instant instant = expiry.minusMillis(1);
        final OneTimePasscodeDelivery delivery = OneTimePasscodeDelivery.builder()
                .expiry(expiry)
                .build();

        assertThat(delivery.hasExpired(instant)).isFalse();
    }

}
