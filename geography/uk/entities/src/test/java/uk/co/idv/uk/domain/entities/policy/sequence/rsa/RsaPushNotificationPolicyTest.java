package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.policy.PushNotificationPolicy;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class RsaPushNotificationPolicyTest {

    private final PushNotificationPolicy policy = new RsaPushNotificationPolicy();

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

}
