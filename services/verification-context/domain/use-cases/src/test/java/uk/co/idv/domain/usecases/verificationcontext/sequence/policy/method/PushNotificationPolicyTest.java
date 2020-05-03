package uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.mobiledevice.MobileDeviceMother;
import uk.co.idv.domain.entities.verificationcontext.method.IneligibleVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationMother;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

class PushNotificationPolicyTest {

    private static final VerificationMethodParams PARAMS = PushNotificationMother.paramsBuilder().build();

    private final PushNotificationPolicy policy = new PushNotificationPolicy(PARAMS);

    @Test
    void shouldReturnMethodName() {
        assertThat(policy.getName()).isEqualTo(PushNotification.NAME);
    }

    @Test
    void shouldReturnEligibleIfIdentityHasAtLeastOneTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneTrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = policy.buildMethod(request);

        assertThat(method).isEqualTo(PushNotificationMother.eligible());
    }

    @Test
    void shouldReturnIneligibleIfIdentityDoesNotHaveTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneUntrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = policy.buildMethod(request);

        assertThat(method).isEqualTo(PushNotificationMother.ineligible());
    }

    @Test
    void shouldPopulateParamsIfIdentityHasAtLeastOneTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneTrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final PushNotification method = (PushNotification) policy.buildMethod(request);

        assertThat(method.getParams()).isEqualTo(PARAMS);
    }

    @Test
    void shouldPopulateIneligibleParamsIfIdentityDoesNotHaveTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneUntrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final PushNotification method = (PushNotification) policy.buildMethod(request);

        assertThat(method.getParams()).isEqualTo(new IneligibleVerificationMethodParams());
    }

}
