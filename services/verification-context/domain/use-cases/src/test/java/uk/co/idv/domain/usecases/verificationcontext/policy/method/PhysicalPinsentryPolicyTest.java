package uk.co.idv.domain.usecases.verificationcontext.policy.method;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.account.AccountMother;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.card.number.CardNumberMother;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentryIneligible;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequestMother;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class PhysicalPinsentryPolicyTest {

    private static final PinsentryFunction FUNCTION = PinsentryFunction.IDENTIFY;
    private static final int MAX_ATTEMPTS = 3;
    private static final Duration DURATION = Duration.ofMinutes(5);

    private final PhysicalPinsentryPolicy parameters = new PhysicalPinsentryPolicy(FUNCTION, MAX_ATTEMPTS, DURATION);

    @Test
    void shouldReturnMethodName() {
        assertThat(parameters.getName()).isEqualTo(PhysicalPinsentry.NAME);
    }

    @Test
    void shouldReturnEligibleIfIdentityHasOpenAccountsWithCard() {
        final CardNumber creditCardNumber = CardNumberMother.credit();
        final Identity identity = IdentityMother.withAccounts(AccountMother.open(creditCardNumber));
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isInstanceOf(PhysicalPinsentryEligible.class);
    }

    @Test
    void shouldReturnIneligibleIfIdentityDoesNotHaveAnyOpenAccounts() {
        final Identity identity = IdentityMother.withAccounts(AccountMother.closed());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method).isInstanceOf(PhysicalPinsentryIneligible.class);
    }

    @Test
    void shouldPopulateCardNumbersFromOpenAccounts() {
        final CardNumber creditCardNumber = CardNumberMother.credit();
        final Identity identity = IdentityMother.withAccounts(AccountMother.open(creditCardNumber));
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final PhysicalPinsentryEligible method = (PhysicalPinsentryEligible) parameters.buildMethod(request);

        assertThat(method.getCardNumbers()).containsExactly(creditCardNumber);
    }

    @Test
    void shouldPopulateFunctionIfIdentityHasOpenAccountsWithCard() {
        final CardNumber creditCardNumber = CardNumberMother.credit();
        final Identity identity = IdentityMother.withAccounts(AccountMother.open(creditCardNumber));
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final PhysicalPinsentryEligible method = (PhysicalPinsentryEligible) parameters.buildMethod(request);

        assertThat(method.getFunction()).isEqualTo(FUNCTION);
    }

    @Test
    void shouldPopulateFunctionIfIdentityDoesNotHaveAnyOpenAccounts() {
        final Identity identity = IdentityMother.withAccounts(AccountMother.closed());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final PhysicalPinsentryIneligible method = (PhysicalPinsentryIneligible) parameters.buildMethod(request);

        assertThat(method.getFunction()).isEqualTo(FUNCTION);
    }

    @Test
    void shouldPopulateMaxAttemptsIfIdentityHasOpenAccountsWithCard() {
        final CardNumber creditCardNumber = CardNumberMother.credit();
        final Identity identity = IdentityMother.withAccounts(AccountMother.open(creditCardNumber));
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method.getMaxAttempts()).isEqualTo(MAX_ATTEMPTS);
    }

    @Test
    void shouldPopulateZeroMaxAttemptsIfIdentityDoesNotHaveAnyOpenAccounts() {
        final Identity identity = IdentityMother.withAccounts(AccountMother.closed());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method.getMaxAttempts()).isEqualTo(0);
    }

    @Test
    void shouldPopulateDurationIfIdentityHasOpenAccountsWithCard() {
        final CardNumber creditCardNumber = CardNumberMother.credit();
        final Identity identity = IdentityMother.withAccounts(AccountMother.open(creditCardNumber));
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method.getDuration()).isEqualTo(DURATION);
    }

    @Test
    void shouldPopulateZeroDurationIfIdentityDoesNotHaveAnyOpenAccounts() {
        final Identity identity = IdentityMother.withAccounts(AccountMother.closed());
        final LoadSequencesRequest request = LoadSequencesRequestMother.withIdentity(identity);

        final VerificationMethod method = parameters.buildMethod(request);

        assertThat(method.getDuration()).isEqualTo(Duration.ZERO);
    }

}
