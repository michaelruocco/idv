package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;
import uk.co.mruoc.idv.domain.model.channel.Rsa;
import uk.co.mruoc.idv.identity.domain.model.CreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.DebitCardNumber;

import java.util.Arrays;
import java.util.function.Predicate;

public class LockoutPolicyRsa extends LockoutPolicyDefault {

    public LockoutPolicyRsa() {
        super(buildAppliesToPolicyPredicate(),
                new LockoutStateCalculatorMaxAttemptsThree(),
                new RecordEveryAttempt());
    }

    private static Predicate<LockoutRequest> buildAppliesToPolicyPredicate() {
        final Predicate<LockoutRequest> appliesToChannel = new PredicateMatchesChannel(Rsa.ID);
        final Predicate<LockoutRequest> appliesToActivity = new PredicateMatchesActivityNames(OnlinePurchase.NAME);
        final Predicate<LockoutRequest> appliesToAlias = new PredicateMatchesAliasTypes(Arrays.asList(
                CreditCardNumber.TYPE,
                DebitCardNumber.TYPE
        ));
        return appliesToChannel
                .and(appliesToActivity)
                .and(appliesToAlias);
    }

}
