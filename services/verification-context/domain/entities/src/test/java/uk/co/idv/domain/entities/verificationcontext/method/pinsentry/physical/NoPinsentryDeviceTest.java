package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Ineligible;

import static org.assertj.core.api.Assertions.assertThat;

class NoPinsentryDeviceTest {

    private final Ineligible ineligible = new NoPinsentryDevice();

    @Test
    void shouldReturnReason() {
        assertThat(ineligible.getReason()).contains("no pinsentry device");
    }

}
