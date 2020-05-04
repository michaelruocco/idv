package uk.co.idv.domain.usecases.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences.NotNextMethodInSequenceException;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OneTimePasscodeVerificationContextLoaderTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String METHOD_NAME = OneTimePasscode.NAME;

    private final VerificationContextLoader contextLoader = mock(VerificationContextLoader.class);

    private final OneTimePasscodeVerificationContextLoader loader = new OneTimePasscodeVerificationContextLoader(contextLoader);

    @Test
    void shouldReturnContextIfContainsOtpAsNextMethod() {
        final VerificationContext expectedContext = mock(VerificationContext.class);
        given(expectedContext.containsSequenceWithNextMethod(METHOD_NAME)).willReturn(true);
        given(contextLoader.load(ID)).willReturn(expectedContext);

        final VerificationContext context = loader.load(ID);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldThrowExceptionIfOtpIsNotNextMethodInContext() {
        final VerificationContext context = mock(VerificationContext.class);
        given(context.containsSequenceWithNextMethod(METHOD_NAME)).willReturn(false);
        given(contextLoader.load(ID)).willReturn(context);

        final Throwable error = catchThrowable(() -> loader.load(ID));

        assertThat(error)
                .isInstanceOf(NotNextMethodInSequenceException.class)
                .hasMessageContaining(METHOD_NAME);
    }

}
