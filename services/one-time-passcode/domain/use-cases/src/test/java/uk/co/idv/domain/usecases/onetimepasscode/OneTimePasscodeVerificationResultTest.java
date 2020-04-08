package uk.co.idv.domain.usecases.onetimepasscode;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Instant;
import java.util.UUID;


class OneTimePasscodeVerificationResultTest {

    private final VerificationResult result = new OneTimePasscodeVerificationResult(
            UUID.randomUUID(),
            Instant.now(),
            true
    );

    @Test
    void shouldReturnOneTimePasscodeMethodName() {
        Assertions.assertThat(result.getMethodName()).isEqualTo(OneTimePasscode.NAME);
    }

}
