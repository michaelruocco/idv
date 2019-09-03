package uk.co.mruoc.verificationcontext.domain;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeSettingsSmsEligibleTest {

    private final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
    private final Collection<String> mobileNumbers = Collections.singleton("07809347780");

    private final OneTimePasscodeSms method = OneTimePasscodeSmsEligible.builder()
            .passcodeSettings(passcodeSettings)
            .mobileNumbers(mobileNumbers)
            .build();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(VerificationMethod.Names.ONE_TIME_PASSCODE_SMS);
    }

    @Test
    void shouldReturnDuration() {
        assertThat(method.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnPasscodeSettings() {
        assertThat(method.getPasscodeSettings()).isEqualTo(passcodeSettings);
    }

    @Test
    void shouldBeEligible() {
        assertThat(method.isEligible()).isTrue();
    }

    @Test
    void shouldReturnEligibleEligibility() {
        assertThat(method.getEligibility()).isInstanceOf(Eligible.class);
    }

    @Test
    void shouldReturnMobileNumbers() {
        assertThat(method.getMobileNumbers()).containsExactlyElementsOf(mobileNumbers);
    }

}
