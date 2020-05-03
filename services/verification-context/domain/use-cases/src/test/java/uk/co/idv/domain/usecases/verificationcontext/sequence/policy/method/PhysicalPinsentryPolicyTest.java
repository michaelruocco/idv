package uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.account.AccountMother;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.card.number.CardNumberMother;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.IneligiblePinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryMother;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequestMother;

import static org.assertj.core.api.Assertions.assertThat;

class PhysicalPinsentryPolicyTest {

    private static final PinsentryParams PARAMS = PhysicalPinsentryMother.paramsBuilder().build();

    private final PhysicalPinsentryPolicy policy = new PhysicalPinsentryPolicy(PARAMS);

    @Test
    void shouldReturnMethodName() {
        assertThat(policy.getName()).isEqualTo(PhysicalPinsentry.NAME);
    }

    @Test
    void shouldReturnEligibleIfIdentityHasOpenAccountsWithCard() {
        final CardNumber creditCardNumber = CardNumberMother.credit();
        final Identity identity = IdentityMother.withAccounts(AccountMother.open(creditCardNumber));
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final PhysicalPinsentry method = (PhysicalPinsentry) policy.buildMethod(request);

        assertThat(method).isEqualToIgnoringGivenFields(PhysicalPinsentryMother.eligible(), "cardNumbers");
        assertThat(method.getCardNumbers()).containsExactly(creditCardNumber);
    }

    @Test
    void shouldReturnIneligibleIfIdentityDoesNotHaveAnyOpenAccounts() {
        final Identity identity = IdentityMother.withAccounts(AccountMother.closed());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = policy.buildMethod(request);

        assertThat(method).isEqualTo(PhysicalPinsentryMother.ineligible());
    }

    @Test
    void shouldPopulateCardNumbersFromOpenAccounts() {
        final CardNumber creditCardNumber = CardNumberMother.credit();
        final Identity identity = IdentityMother.withAccounts(AccountMother.open(creditCardNumber));
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final PhysicalPinsentry method = (PhysicalPinsentry) policy.buildMethod(request);

        assertThat(method.getCardNumbers()).containsExactly(creditCardNumber);
    }

    @Test
    void shouldPopulateParamsIfIdentityHasOpenAccountsWithCard() {
        final CardNumber creditCardNumber = CardNumberMother.credit();
        final Identity identity = IdentityMother.withAccounts(AccountMother.open(creditCardNumber));
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final PhysicalPinsentry method = (PhysicalPinsentry) policy.buildMethod(request);

        assertThat(method.getParams()).isEqualTo(PARAMS);
    }

    @Test
    void shouldPopulateIneligibleParamsIfIdentityDoesOpenAccountWithCard() {
        final Identity identity = IdentityMother.withAccounts(AccountMother.closed());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final PhysicalPinsentry method = (PhysicalPinsentry) policy.buildMethod(request);

        assertThat(method.getParams()).isEqualTo(new IneligiblePinsentryParams(PARAMS.getFunction()));
    }

}
