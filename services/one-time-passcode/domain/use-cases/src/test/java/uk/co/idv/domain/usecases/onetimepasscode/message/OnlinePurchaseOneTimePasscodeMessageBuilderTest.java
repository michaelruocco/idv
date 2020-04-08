package uk.co.idv.domain.usecases.onetimepasscode.message;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.domain.entities.activity.OnlinePurchase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class OnlinePurchaseOneTimePasscodeMessageBuilderTest {

    private static final String PASSCODE = "12345678";

    private final OneTimePasscodeMessageBuilder builder = new OnlinePurchaseOneTimePasscodeMessageBuilder();

    @Test
    void shouldSupportOnlinePurchaseActivity() {
        boolean supported = builder.supports(OnlinePurchase.NAME);

        assertThat(supported).isTrue();
    }

    @Test
    void shouldNotSupportAnyOtherActivity() {
        boolean supported = builder.supports("other-activity");

        assertThat(supported).isFalse();
    }

    @Test
    void shouldBuildOnlinePurchaseMessage() {
        final OnlinePurchase onlinePurchase = ActivityMother.onlinePurchase();

        final String message = builder.build(onlinePurchase, PASSCODE);

        final String expectedMessage = toExpectedMessage(onlinePurchase);
        assertThat(message).isEqualTo(expectedMessage);
    }

    @Test
    void shouldThrowExceptionIfActivityIsNotOnlinePurchase() {
        final Activity activity = ActivityMother.fake();

        final Throwable error = catchThrowable(() -> builder.build(activity, PASSCODE));

        assertThat(error).isInstanceOf(ClassCastException.class);
    }

    private static String toExpectedMessage(final OnlinePurchase onlinePurchase) {
        return String.format("Your verification code for your purchase from %s using card number %s for amount %s is %s",
                onlinePurchase.getMerchantName(),
                onlinePurchase.getTokenizedCardNumber(),
                onlinePurchase.getCost(),
                PASSCODE);
    }

}
