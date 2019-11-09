package uk.co.idv.domain.entities.verificationcontext.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeSmsEligibleTest {

    private final PasscodeSettings passcodeSettings = new DefaultPasscodeSettings();
    private final Collection<MobileNumber> mobileNumbers = Collections.singleton(new MobileNumber("07809347780"));

    private final OneTimePasscodeSmsEligible method = new OneTimePasscodeSmsEligible(passcodeSettings, mobileNumbers);

    @Test
    void shouldReturnName() {
        assertThat(method.getName()).isEqualTo(OneTimePasscodeSms.NAME);
    }

    @Test
    void shouldReturnMaxAttempts() {
        assertThat(method.getMaxAttempts()).isEqualTo(1);
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

    @Test
    void shouldAddResult() {
        final VerificationResult result = new FakeVerificationResultSuccessful(OneTimePasscodeSms.NAME);

        final OneTimePasscodeSmsEligible methodWithResult = (OneTimePasscodeSmsEligible) method.addResult(result);

        assertThat(methodWithResult).isEqualToIgnoringGivenFields(method, "results", "mobileNumbers");
        assertThat(methodWithResult.getMobileNumbers()).containsExactlyElementsOf(mobileNumbers);
        assertThat(methodWithResult.getResults()).containsExactly(result);
    }

}
