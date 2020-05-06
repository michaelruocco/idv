package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.policy.MobilePinsentryPolicy;

import static org.assertj.core.api.Assertions.assertThat;

class RsaMobilePinsentryPolicyTest {

    private final MobilePinsentryPolicy policy = new RsaMobilePinsentryPolicy();

    @Test
    void shouldReturnRsaPinsentryParams() {
        assertThat(policy.getParams()).isInstanceOf(RsaPinsentryParams.class);
    }

}
