package uk.co.idv.domain.usecases.verificationcontext.method;

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

class MobilePinsentryParametersTest {

    private static final PinsentryFunction FUNCTION = PinsentryFunction.IDENTIFY;
    private static final int MAX_ATTEMPTS = 3;
    private static final Duration DURATION = Duration.ofMinutes(5);

    private final MobilePinsentryParameters parameters = new MobilePinsentryParameters(FUNCTION, MAX_ATTEMPTS, DURATION);

    @Test
    void shouldReturnMethodName() {
        assertThat(parameters.getMethodName()).isEqualTo(MobilePinsentry.NAME);
    }

    @Test
    void shouldReturnEligibleMethodWithParameterValuesIfIdentityHasAtLeastOneTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneTrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isEqualTo(new MobilePinsentryEligible(FUNCTION, MAX_ATTEMPTS, DURATION));
    }

    @Test
    void shouldReturnEligibleIfIdentityDoesNotHaveTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneUntrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isEqualTo(new MobilePinsentryIneligible(FUNCTION));
    }

}
