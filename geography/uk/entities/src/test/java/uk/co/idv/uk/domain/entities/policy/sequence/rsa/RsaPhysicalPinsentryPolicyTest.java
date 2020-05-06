package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.policy.PhysicalPinsentryPolicy;

import static org.assertj.core.api.Assertions.assertThat;

class RsaPhysicalPinsentryPolicyTest {

    private final PhysicalPinsentryPolicy policy = new RsaPhysicalPinsentryPolicy();

    @Test
    void shouldReturnRsaPinsentryParams() {
        assertThat(policy.getParams()).isInstanceOf(RsaPinsentryParams.class);
    }

}
