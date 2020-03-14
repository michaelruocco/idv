package uk.co.idv.domain.usecases.verification.onetimepasscode.message;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.domain.entities.activity.OnlinePurchase;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultOneTimePasscodeMessageBuilderTest {

    private static final String PASSCODE = "12345678";

    private final OneTimePasscodeMessageBuilder builder = new DefaultOneTimePasscodeMessageBuilder();

    @Test
    void shouldSupportAnyActivity() {
        boolean supported = builder.supports("any-activity");

        assertThat(supported).isTrue();
    }

    @Test
    void shouldBuildMessage() {
        final OnlinePurchase onlinePurchase = ActivityMother.onlinePurchase();

        final String message = builder.build(onlinePurchase, PASSCODE);

        assertThat(message).isEqualTo(expectedMessage());
    }

    private static String expectedMessage() {
        return String.format("Your verification code is %s", PASSCODE);
    }

}
