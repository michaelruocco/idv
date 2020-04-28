package uk.co.idv.domain.usecases.verificationcontext.policy.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.mobiledevice.MobileDeviceMother;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryIneligible;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequestMother;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class MobilePinsentryPolicyTest {

    private static final PinsentryFunction FUNCTION = PinsentryFunction.IDENTIFY;
    private static final int MAX_ATTEMPTS = 3;
    private static final Duration DURATION = Duration.ofMinutes(5);

    private final MobilePinsentryPolicy parameters = new MobilePinsentryPolicy(FUNCTION, MAX_ATTEMPTS, DURATION);

    @Test
    void shouldReturnMethodName() {
        assertThat(parameters.getName()).isEqualTo(MobilePinsentry.NAME);
    }

    @Test
    void shouldReturnEligibleIfIdentityHasAtLeastOneTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneTrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isInstanceOf(MobilePinsentryEligible.class);
    }

    @Test
    void shouldReturnIneligibleIfIdentityDoesNotHaveTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneUntrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isInstanceOf(MobilePinsentryIneligible.class);
    }

    @Test
    void shouldPopulateMaxAttemptsIfIdentityHasAtLeastOneTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneTrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method.getMaxAttempts()).isEqualTo(MAX_ATTEMPTS);
    }

    @Test
    void shouldPopulateZeroMaxAttemptsIfIdentityDoesNotHaveTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneUntrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method.getMaxAttempts()).isEqualTo(0);
    }

    @Test
    void shouldPopulateDurationIfIdentityHasAtLeastOneTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneTrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final MobilePinsentryEligible method = (MobilePinsentryEligible) parameters.buildMethod(request);

        assertThat(method.getDuration()).isEqualTo(DURATION);
    }

    @Test
    void shouldPopulateZeroDurationIfIdentityDoesNotHaveTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneUntrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method.getDuration()).isEqualTo(Duration.ZERO);
    }

    @Test
    void shouldPopulateFunctionIfIdentityHasAtLeastOneTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneTrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final MobilePinsentryEligible method = (MobilePinsentryEligible) parameters.buildMethod(request);

        assertThat(method.getFunction()).isEqualTo(FUNCTION);
    }

    @Test
    void shouldPopulateFunctionIfIdentityDoesNotHaveTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneUntrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final MobilePinsentryIneligible method = (MobilePinsentryIneligible) parameters.buildMethod(request);

        assertThat(method.getFunction()).isEqualTo(FUNCTION);
    }

}
