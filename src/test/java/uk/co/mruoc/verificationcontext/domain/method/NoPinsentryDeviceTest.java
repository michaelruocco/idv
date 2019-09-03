package uk.co.mruoc.verificationcontext.domain.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NoPinsentryDeviceTest {

    private final Ineligible ineligible = new NoPinsentryDevice();

    @Test
    void shouldReturnReason() {
        assertThat(ineligible.reason()).contains("no pinsentry device");
    }

}
