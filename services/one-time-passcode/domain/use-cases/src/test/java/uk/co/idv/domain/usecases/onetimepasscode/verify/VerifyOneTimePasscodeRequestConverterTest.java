package uk.co.idv.domain.usecases.onetimepasscode.verify;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationAttempt;
import uk.co.idv.domain.usecases.util.time.FakeTimeProvider;
import uk.co.idv.domain.usecases.util.time.TimeProvider;

import java.time.Instant;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class VerifyOneTimePasscodeRequestConverterTest {

    private final Instant now = Instant.now();
    private final TimeProvider timeProvider = new FakeTimeProvider(now);

    private final VerifyOneTimePasscodeRequestConverter converter = new VerifyOneTimePasscodeRequestConverter(timeProvider);

    @Test
    void shouldConvertToAttemptForEachPasscode() {
        final String passcode1 = "11111111";
        final String passcode2 = "22222222";
        final VerifyOneTimePasscodeRequest request = VerifyOneTimePasscodeRequestMother.build(
                passcode1,
                passcode2
        );

        final Collection<OneTimePasscodeVerificationAttempt> attempts = converter.toAttempts(request);

        assertThat(attempts).containsExactly(
                new OneTimePasscodeVerificationAttempt(now, passcode1),
                new OneTimePasscodeVerificationAttempt(now, passcode2)
        );
    }

}
