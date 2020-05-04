package uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.mobiledevice.MobileDeviceMother;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.DefaultPinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.IneligiblePinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentryMother;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

class MobilePinsentryPolicyTest {

    private static final DefaultPinsentryParams PARAMS = MobilePinsentryMother.paramsBuilder().build();

    private final MobilePinsentryPolicy parameters = new MobilePinsentryPolicy(PARAMS);

    @Test
    void shouldReturnMethodName() {
        assertThat(parameters.getName()).isEqualTo(MobilePinsentry.NAME);
    }

    @Test
    void shouldReturnEligibleIfIdentityHasAtLeastOneTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneTrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isEqualToIgnoringGivenFields(MobilePinsentryMother.eligible());
    }

    @Test
    void shouldReturnIneligibleIfIdentityDoesNotHaveTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneUntrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isEqualToIgnoringGivenFields(MobilePinsentryMother.ineligible());
    }

    @Test
    void shouldPopulateParamsIfIdentityHasAtLeastOneTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneTrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final MobilePinsentry method = (MobilePinsentry) parameters.buildMethod(request);

        assertThat(method.getParams()).isEqualTo(PARAMS);
    }

    @Test
    void shouldPopulateIneligibleParamsIfIdentityDoesNotHaveTrustedDevice() {
        final Identity identity = IdentityMother.withMobileDevices(MobileDeviceMother.oneUntrusted());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final MobilePinsentry method = (MobilePinsentry) parameters.buildMethod(request);

        assertThat(method.getParams()).isEqualTo(new IneligiblePinsentryParams(PARAMS.getFunction()));
    }

}
