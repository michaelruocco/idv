package uk.co.idv.domain.usecases.verification.onetimepasscode.message;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.domain.usecases.exception.ActivityNotSupportedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DelegatingOneTimePasscodeMessageBuilderTest {

    private final OneTimePasscodeMessageBuilder builder1 = mock(OneTimePasscodeMessageBuilder.class);
    private final OneTimePasscodeMessageBuilder builder2 = mock(OneTimePasscodeMessageBuilder.class);

    private final OneTimePasscodeMessageBuilder builder = new DelegatingOneTimePasscodeMessageBuilder(
            builder1,
            builder2
    );

    @Test
    void shouldReturnNotSupportedIfAllBuildersDoNotSupportActivityName() {
        final String activityName = "activity-name";
        given(builder1.supports(activityName)).willReturn(false);
        given(builder2.supports(activityName)).willReturn(false);

        final boolean supported = builder.supports(activityName);

        assertThat(supported).isFalse();
    }

    @Test
    void shouldReturnSupportedIfOneBuilderSupportsActivityName() {
        final String activityName = "activity-name";
        given(builder1.supports(activityName)).willReturn(false);
        given(builder2.supports(activityName)).willReturn(true);

        final boolean supported = builder.supports(activityName);

        assertThat(supported).isTrue();
    }

    @Test
    void shouldThrowExceptionIfNoBuildersSupportActivity() {
        final String passcode = "12345678";
        final Activity activity = ActivityMother.onlinePurchase();
        given(builder1.supports(activity.getName())).willReturn(false);
        given(builder2.supports(activity.getName())).willReturn(false);

        final Throwable error = catchThrowable(() -> builder.build(activity, passcode));

        assertThat(error)
                .isInstanceOf(ActivityNotSupportedException.class)
                .hasMessage(activity.getName());
    }

    @Test
    void shouldReturnMessageBuiltFromSupportedBuilder() {
        final String passcode = "12345678";
        final Activity activity = ActivityMother.onlinePurchase();
        given(builder1.supports(activity.getName())).willReturn(false);
        given(builder2.supports(activity.getName())).willReturn(true);
        final String expectedMessage = "message";
        given(builder2.build(activity, passcode)).willReturn(expectedMessage);

        final String message = builder.build(activity, passcode);

        assertThat(message).isEqualTo(expectedMessage);
    }

}
