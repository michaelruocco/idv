package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeSmsEligibleTest {

    private final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
    private final Collection<MobileNumber> mobileNumbers = Collections.singleton(new MobileNumber("07809347780"));

    private final OneTimePasscodeSms method = OneTimePasscodeSmsEligible.builder()
            .passcodeSettings(passcodeSettings)
            .mobileNumbers(mobileNumbers)
            .build();

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo("one-time-passcode-sms");
    }

    @Test
    void shouldReturnDuration() {
        assertThat(method.getDuration()).isEqualTo(Duration.ofMinutes(5));
    }

    @Test
    void shouldReturnEligibility() {
        assertThat(method.getEligibility()).isEqualTo(new Eligible());
    }

    @Test
    void shouldReturnPasscodeSettings() {
        assertThat(method.getPasscodeSettings()).isEqualTo(passcodeSettings);
    }

    @Test
    void shouldReturnMobileNumbers() {
        assertThat(method.getMobileNumbers()).containsExactlyElementsOf(mobileNumbers);
    }

}