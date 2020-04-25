package uk.co.idv.domain.usecases.verificationcontext.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.mobiledevice.MobileDeviceMother;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationIneligible;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequestMother;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class PushNotificationParametersTest {

    private static final int MAX_ATTEMPTS = 3;
    private static final Duration DURATION = Duration.ofMinutes(5);

    private final PushNotificationParameters parameters = new PushNotificationParameters(MAX_ATTEMPTS, DURATION);

    @Test
    void shouldReturnMethodName() {
        assertThat(parameters.getMethodName()).isEqualTo(PushNotification.NAME);
    }

    @Test
    void shouldReturnEligibleMethodWithParameterValuesIfIdentityHasAtLeastOneTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneTrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isEqualTo(new PushNotificationEligible(MAX_ATTEMPTS, DURATION));
    }

    @Test
    void shouldReturnEligibleIfIdentityDoesNotHaveTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneUntrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isEqualTo(new PushNotificationIneligible());
    }

}
