package uk.co.mruoc.verificationcontext.domain.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeSmsIneligibleTest {

    private final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();

    private final OneTimePasscodeSms method = new OneTimePasscodeSmsIneligible(passcodeSettings);

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(new NoEligibleMobileNumbers());
    }

    @Test
    void shouldReturnPasscodeSettings() {
        assertThat(method.getPasscodeSettings()).isEqualTo(passcodeSettings);
    }

    @Test
    void shouldReturnEmptyMobileNumbers() {
        assertThat(method.getMobileNumbers()).isEmpty();
    }

}
