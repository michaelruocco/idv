package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.PasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.policy.OneTimePasscodePolicy;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class RsaOneTimePasscodePolicyTest {

    private final OneTimePasscodePolicy policy = new RsaOneTimePasscodePolicy();

    @Test
    void shouldReturnMaxAttempts() {
        final VerificationMethodParams params = policy.getParams();

        assertThat(params.getMaxAttempts()).isEqualTo(5);
    }

    @Test
    void shouldReturnDuration() {
        final VerificationMethodParams params = policy.getParams();

        assertThat(params.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnPasscodeLength() {
        final PasscodeParams params = policy.getParams().getPasscodeParams();

        assertThat(params.getLength()).isEqualTo(8);
    }

    @Test
    void shouldReturnPasscodeDuration() {
        final PasscodeParams params = policy.getParams().getPasscodeParams();

        assertThat(params.getLength()).isEqualTo(8);
    }

    @Test
    void shouldReturnMaxDeliveries() {
        final PasscodeParams params = policy.getParams().getPasscodeParams();

        assertThat(params.getMaxDeliveries()).isEqualTo(3);
    }

}
