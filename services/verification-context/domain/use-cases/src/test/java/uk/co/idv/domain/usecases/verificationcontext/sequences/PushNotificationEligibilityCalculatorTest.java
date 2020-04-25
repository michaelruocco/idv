package uk.co.idv.domain.usecases.verificationcontext.sequences;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.mobiledevice.MobileDeviceMother;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationIneligible;
import uk.co.idv.domain.usecases.verificationcontext.LoadSequencesRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PushNotificationEligibilityCalculatorTest {

    private final PushNotificationParameters parameters = mock(PushNotificationParameters.class);
    private final EligibilityCalculator calculator = new PushNotificationEligibilityCalculator(parameters);

    @Test
    void shouldReturnEligibleIfIdentityHasAtLeastOneTrustedDevice() {
        final VerificationMethod expectedMethod = new PushNotificationEligible();
        given(parameters.buildEligibleMethod()).willReturn(expectedMethod);
        final LoadSequencesRequest request = LoadSequencesRequest.builder()
                .identity(IdentityMother.build())
                .build();

        final VerificationMethod method = calculator.calculate(request);

        assertThat(method).isEqualTo(expectedMethod);
    }

    @Test
    void shouldReturnEligibleIfIdentityDoesNotHaveTrustedDevice() {
        final Identity identity = IdentityMother.emptyDataBuilder()
                .mobileDevices(MobileDeviceMother.oneUntrusted())
                .build();
        final LoadSequencesRequest request = LoadSequencesRequest.builder()
                .identity(identity)
                .build();

        final VerificationMethod method = calculator.calculate(request);

        assertThat(method).isEqualTo(new PushNotificationIneligible());
    }

}