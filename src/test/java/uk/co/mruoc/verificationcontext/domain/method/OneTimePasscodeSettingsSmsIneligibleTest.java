package uk.co.mruoc.verificationcontext.domain.method;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeSettingsSmsIneligibleTest {

    private final Ineligible ineligible = new Ineligible("no mobile numbers available");
    private final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();

    private final OneTimePasscodeSms method = OneTimePasscodeSmsIneligible.builder()
            .ineligible(ineligible)
            .passcodeSettings(passcodeSettings)
            .build();

    @Test
    void shouldReturnIneligibleEligibility() {
        assertThat(method.getEligibility()).isEqualTo(ineligible);
    }

    @Test
    void shouldReturnEmptyMobileNumbers() {
        assertThat(method.getMobileNumbers()).isEmpty();
    }

}
