package uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.mobiledevice.MobileDeviceMother;
import uk.co.idv.domain.entities.verificationcontext.method.params.IneligibleVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.params.VerificationMethodParamsMother;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationMother;
import uk.co.idv.domain.usecases.verificationcontext.sequence.policy.VerificationSequencesPolicyRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PushNotificationPolicyTest {

    private static final VerificationMethodParams PARAMS = VerificationMethodParamsMother.eligible();

    private final PushNotificationPolicy policy = new PushNotificationPolicy(PARAMS);

    @Test
    void shouldReturnMethodName() {
        assertThat(policy.getName()).isEqualTo(PushNotification.NAME);
    }

    @Test
    void shouldReturnEligibleIfIdentityHasAtLeastOneTrustedDevice() {
        final VerificationSequencesPolicyRequest request = mock(VerificationSequencesPolicyRequest.class);
        given(request.getMobileDevices()).willReturn(MobileDeviceMother.oneTrusted());

        final VerificationMethod method = policy.buildMethod(request);

        assertThat(method).isEqualTo(PushNotificationMother.eligible());
    }

    @Test
    void shouldReturnIneligibleIfIdentityDoesNotHaveTrustedDevice() {
        final VerificationSequencesPolicyRequest request = mock(VerificationSequencesPolicyRequest.class);
        given(request.getMobileDevices()).willReturn(MobileDeviceMother.oneUntrusted());

        final VerificationMethod method = policy.buildMethod(request);

        assertThat(method).isEqualTo(PushNotificationMother.ineligible());
    }

    @Test
    void shouldPopulateParamsIfIdentityHasAtLeastOneTrustedDevice() {
        final VerificationSequencesPolicyRequest request = mock(VerificationSequencesPolicyRequest.class);
        given(request.getMobileDevices()).willReturn(MobileDeviceMother.oneTrusted());

        final PushNotification method = (PushNotification) policy.buildMethod(request);

        assertThat(method.getParams()).isEqualTo(PARAMS);
    }

    @Test
    void shouldPopulateIneligibleParamsIfIdentityDoesNotHaveTrustedDevice() {
        final VerificationSequencesPolicyRequest request = mock(VerificationSequencesPolicyRequest.class);
        given(request.getMobileDevices()).willReturn(MobileDeviceMother.oneUntrusted());

        final PushNotification method = (PushNotification) policy.buildMethod(request);

        assertThat(method.getParams()).isEqualTo(new IneligibleVerificationMethodParams());
    }

}
