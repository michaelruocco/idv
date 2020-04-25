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
        final Identity identity = IdentityMother.emptyDataBuilder()
                .mobileDevices(MobileDeviceMother.oneTrusted())
                .build();
        final LoadSequencesRequest request = toRequest(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isEqualTo(new PushNotificationEligible(MAX_ATTEMPTS, DURATION));
    }

    @Test
    void shouldReturnEligibleIfIdentityDoesNotHaveTrustedDevice() {
        final Identity identity = IdentityMother.emptyDataBuilder()
                .mobileDevices(MobileDeviceMother.oneUntrusted())
                .build();
        final LoadSequencesRequest request = toRequest(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isEqualTo(new PushNotificationIneligible());
    }

    private static LoadSequencesRequest toRequest(final Identity identity) {
        return LoadSequencesRequest.builder()
                .identity(identity)
                .build();
    }

}
