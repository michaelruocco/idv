package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.policy;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.account.AccountMother;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.card.number.CardNumberMother;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodPolicyRequest;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.IneligiblePinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParamsMother;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryMother;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PhysicalPinsentryPolicyTest {

    private static final PinsentryParams PARAMS = PinsentryParamsMother.eligible();

    private final PhysicalPinsentryPolicy policy = new PhysicalPinsentryPolicy(PARAMS);

    @Test
    void shouldReturnMethodName() {
        assertThat(policy.getName()).isEqualTo(PhysicalPinsentry.NAME);
    }

    @Test
    void shouldReturnEligibleIfIdentityHasOpenAccountsWithCard() {
        final CardNumber creditCardNumber = CardNumberMother.credit();
        final VerificationMethodPolicyRequest request = mock(VerificationMethodPolicyRequest.class);
        given(request.getAccounts()).willReturn(Collections.singleton(AccountMother.open(creditCardNumber)));

        final PhysicalPinsentry method = (PhysicalPinsentry) policy.buildMethod(request);

        assertThat(method).isEqualToIgnoringGivenFields(PhysicalPinsentryMother.eligible(), "cardNumbers");
        assertThat(method.getCardNumbers()).containsExactly(creditCardNumber);
    }

    @Test
    void shouldReturnIneligibleIfIdentityDoesNotHaveAnyOpenAccounts() {
        final VerificationMethodPolicyRequest request = mock(VerificationMethodPolicyRequest.class);
        given(request.getAccounts()).willReturn(Collections.singleton(AccountMother.closed()));

        final VerificationMethod method = policy.buildMethod(request);

        assertThat(method).isEqualTo(PhysicalPinsentryMother.ineligible());
    }

    @Test
    void shouldPopulateCardNumbersFromOpenAccounts() {
        final CardNumber creditCardNumber = CardNumberMother.credit();
        final VerificationMethodPolicyRequest request = mock(VerificationMethodPolicyRequest.class);
        given(request.getAccounts()).willReturn(Collections.singleton(AccountMother.open(creditCardNumber)));

        final PhysicalPinsentry method = (PhysicalPinsentry) policy.buildMethod(request);

        assertThat(method.getCardNumbers()).containsExactly(creditCardNumber);
    }

    @Test
    void shouldPopulateParamsIfIdentityHasOpenAccountsWithCard() {
        final CardNumber creditCardNumber = CardNumberMother.credit();
        final VerificationMethodPolicyRequest request = mock(VerificationMethodPolicyRequest.class);
        given(request.getAccounts()).willReturn(Collections.singleton(AccountMother.open(creditCardNumber)));

        final PhysicalPinsentry method = (PhysicalPinsentry) policy.buildMethod(request);

        assertThat(method.getParams()).isEqualTo(PARAMS);
    }

    @Test
    void shouldPopulateIneligibleParamsIfIdentityDoesOpenAccountWithCard() {
        final VerificationMethodPolicyRequest request = mock(VerificationMethodPolicyRequest.class);
        given(request.getAccounts()).willReturn(Collections.singleton(AccountMother.closed()));

        final PhysicalPinsentry method = (PhysicalPinsentry) policy.buildMethod(request);

        assertThat(method.getParams()).isEqualTo(new IneligiblePinsentryParams(PARAMS.getFunction()));
    }

}
