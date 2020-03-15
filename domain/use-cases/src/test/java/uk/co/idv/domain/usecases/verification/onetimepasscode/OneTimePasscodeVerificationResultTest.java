package uk.co.idv.domain.usecases.verification.onetimepasscode;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeVerificationResultTest {

    private final VerificationResult result = new OneTimePasscodeVerificationResult(
            UUID.randomUUID(),
            Instant.now(),
            true
    );

    @Test
    void shouldReturnOneTimePasscodeMethodName() {
        assertThat(result.getMethodName()).isEqualTo(OneTimePasscode.NAME);
    }

}
