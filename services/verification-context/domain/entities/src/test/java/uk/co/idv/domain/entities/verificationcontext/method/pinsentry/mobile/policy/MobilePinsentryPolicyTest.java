package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.mobiledevice.MobileDeviceMother;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodPolicyRequest;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryMother;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.IneligiblePinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParamsMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MobilePinsentryPolicyTest {

    private static final PinsentryParams PARAMS = PinsentryParamsMother.eligible();

    private final MobilePinsentryPolicy parameters = new MobilePinsentryPolicy(PARAMS);

    @Test
    void shouldReturnMethodName() {
        assertThat(parameters.getName()).isEqualTo(MobilePinsentry.NAME);
    }

    @Test
    void shouldReturnEligibleIfIdentityHasAtLeastOneTrustedDevice() {
        final VerificationMethodPolicyRequest request = mock(VerificationMethodPolicyRequest.class);
        given(request.getMobileDevices()).willReturn(MobileDeviceMother.oneTrusted());

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isEqualToIgnoringGivenFields(MobilePinsentryMother.eligible());
    }

    @Test
    void shouldReturnIneligibleIfIdentityDoesNotHaveTrustedDevice() {
        final VerificationMethodPolicyRequest request = mock(VerificationMethodPolicyRequest.class);
        given(request.getMobileDevices()).willReturn(MobileDeviceMother.oneUntrusted());

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isEqualToIgnoringGivenFields(MobilePinsentryMother.ineligible());
    }

    @Test
    void shouldPopulateParamsIfIdentityHasAtLeastOneTrustedDevice() {
        final VerificationMethodPolicyRequest request = mock(VerificationMethodPolicyRequest.class);
        given(request.getMobileDevices()).willReturn(MobileDeviceMother.oneTrusted());

        final MobilePinsentry method = (MobilePinsentry) parameters.buildMethod(request);

        assertThat(method.getParams()).isEqualTo(PARAMS);
    }

    @Test
    void shouldPopulateIneligibleParamsIfIdentityDoesNotHaveTrustedDevice() {
        final VerificationMethodPolicyRequest request = mock(VerificationMethodPolicyRequest.class);
        given(request.getMobileDevices()).willReturn(MobileDeviceMother.oneUntrusted());

        final MobilePinsentry method = (MobilePinsentry) parameters.buildMethod(request);

        assertThat(method.getParams()).isEqualTo(new IneligiblePinsentryParams(PARAMS.getFunction()));
    }

}
