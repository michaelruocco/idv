package uk.co.idv.domain.usecases.verificationcontext.sequence.policy.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.card.account.OpenAccount;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.DefaultPinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.IneligiblePinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.NoEligibleCards;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PhysicalPinsentryPolicy implements MethodPolicy {

    private final DefaultPinsentryParams params;

    @Override
    public String getName() {
        return PhysicalPinsentry.NAME;
    }

    @Override
    public VerificationMethod buildMethod(final LoadSequencesRequest request) {
        final Collection<CardNumber> eligibleCardNumbers = toEligibleCardNumbers(request);
        if (eligibleCardNumbers.isEmpty()) {
            return toIneligible(eligibleCardNumbers);
        }
        return toEligible(eligibleCardNumbers);
    }

    private static Collection<CardNumber> toEligibleCardNumbers(final LoadSequencesRequest request) {
        final Collection<Account> accounts = request.getAccounts();
        return accounts.stream()
                .filter(PhysicalPinsentryPolicy::isEligible)
                .map(Account::getCardNumbers)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private static boolean isEligible(final Account account) {
        return account.getStatus().equals(OpenAccount.STATUS);
    }

    private VerificationMethod toIneligible(final Collection<CardNumber> cardNumbers) {
        return  PhysicalPinsentry.ineligibleBuilder()
                .params(new IneligiblePinsentryParams(params.getFunction()))
                .cardNumbers(cardNumbers)
                .eligibility(new NoEligibleCards())
                .build();
    }

    private VerificationMethod toEligible(final Collection<CardNumber> cardNumbers) {
        return PhysicalPinsentry.eligibleBuilder()
                .params(params)
                .cardNumbers(cardNumbers)
                .build();
    }

}
