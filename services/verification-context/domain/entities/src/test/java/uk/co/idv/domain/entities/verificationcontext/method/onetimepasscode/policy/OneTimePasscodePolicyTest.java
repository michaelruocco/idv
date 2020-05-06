package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.phonenumber.PhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodPolicyRequest;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeMother;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.SmsDeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParamsMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class OneTimePasscodePolicyTest {

    private static final OneTimePasscodeParams PARAMS = OneTimePasscodeParamsMother.eligible();

    private final OneTimePasscodePolicy parameters = new OneTimePasscodePolicy(PARAMS);

    @Test
    void shouldReturnMethodName() {
        assertThat(parameters.getName()).isEqualTo(OneTimePasscode.NAME);
    }

    @Test
    void shouldReturnEligibleIfIdentityHasAtLeastOneMobilePhoneNumber() {
        final PhoneNumber phoneNumber = PhoneNumberMother.mobile();
        final VerificationMethodPolicyRequest request = mock(VerificationMethodPolicyRequest.class);
        given(request.getPhoneNumbers()).willReturn(new PhoneNumbers(phoneNumber));

        final OneTimePasscode method = (OneTimePasscode) parameters.buildMethod(request);

        final OneTimePasscode expectedMethod = OneTimePasscodeMother.eligible();
        assertThat(method).isEqualToIgnoringGivenFields(expectedMethod, "deliveryMethods");
        assertThat(method.getDeliveryMethods()).containsExactlyElementsOf(expectedMethod.getDeliveryMethods());

    }

    @Test
    void shouldReturnIneligibleIfIdentityDoesNotHaveAnyMobilePhoneNumbers() {
        final PhoneNumber phoneNumber = PhoneNumberMother.other();
        final VerificationMethodPolicyRequest request = mock(VerificationMethodPolicyRequest.class);
        given(request.getPhoneNumbers()).willReturn(new PhoneNumbers(phoneNumber));

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isEqualTo(OneTimePasscodeMother.ineligible());
    }

    @Test
    void shouldPopulateSmsDeliveryMethodForMobilePhoneNumber() {
        final PhoneNumber phoneNumber = PhoneNumberMother.mobile();
        final VerificationMethodPolicyRequest request = mock(VerificationMethodPolicyRequest.class);
        given(request.getPhoneNumbers()).willReturn(new PhoneNumbers(phoneNumber));

        final OneTimePasscode method = (OneTimePasscode) parameters.buildMethod(request);

        assertThat(method.getDeliveryMethods()).containsExactly(new SmsDeliveryMethod(
                phoneNumber.getId(),
                phoneNumber.getValue()
        ));
    }

}
