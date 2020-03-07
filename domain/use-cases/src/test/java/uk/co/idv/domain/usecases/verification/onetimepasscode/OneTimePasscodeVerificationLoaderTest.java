package uk.co.idv.domain.usecases.verification.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.usecases.util.FakeTimeGenerator;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeVerificationLoader.OneTimePasscodeVerificationExpiredException;
import uk.co.idv.domain.usecases.verification.onetimepasscode.OneTimePasscodeVerificationLoader.OneTimePasscodeVerificationNotFoundException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OneTimePasscodeVerificationLoaderTest {

    private final Instant now = Instant.now();

    private final OneTimePasscodeVerificationDao dao = mock(OneTimePasscodeVerificationDao.class);
    private final TimeGenerator timeGenerator = new FakeTimeGenerator(now);

    private final OneTimePasscodeVerificationLoader loader = OneTimePasscodeVerificationLoader.builder()
            .dao(dao)
            .timeGenerator(timeGenerator)
            .build();

    @Test
    public void shouldThrowExceptionIfVerificationNotFound() {
        final UUID id = UUID.randomUUID();
        given(dao.load(id)).willReturn(Optional.empty());

        final Throwable error = catchThrowable(() -> loader.load(id));

        assertThat(error)
                .isInstanceOf(OneTimePasscodeVerificationNotFoundException.class)
                .hasMessage(id.toString());
    }

    @Test
    public void shouldThrowExceptionIfVerificationHasExpired() {
        final UUID id = UUID.randomUUID();
        final OneTimePasscodeVerification verification = mock(OneTimePasscodeVerification.class);
        given(verification.hasExpired(now)).willReturn(true);
        given(dao.load(id)).willReturn(Optional.of(verification));

        final Throwable error = catchThrowable(() -> loader.load(id));

        assertThat(error).isInstanceOf(OneTimePasscodeVerificationExpiredException.class);
    }

    @Test
    public void shouldReturnVerification() {
        final UUID id = UUID.randomUUID();
        final OneTimePasscodeVerification expectedVerification = mock(OneTimePasscodeVerification.class);
        given(dao.load(id)).willReturn(Optional.of(expectedVerification));

        final OneTimePasscodeVerification verification = loader.load(id);

        assertThat(verification).isEqualTo(expectedVerification);
    }

}
