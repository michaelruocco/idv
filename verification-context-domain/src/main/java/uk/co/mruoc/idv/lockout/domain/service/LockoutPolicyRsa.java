package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;
import uk.co.mruoc.idv.domain.model.channel.Rsa;
import uk.co.mruoc.idv.identity.domain.model.CreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.DebitCardNumber;
import uk.co.mruoc.idv.lockout.domain.service.PolicyAppliesToRequestPredicate.DefaultPolicyPredicateBuilder;

import java.util.function.Predicate;

public class LockoutPolicyRsa extends LockoutPolicyDefault {

    private static final int MAX_NUMBER_OF_ATTEMPTS = 3;

    public LockoutPolicyRsa() {
        super(buildAppliesToPolicyPredicate(),
                new LockoutStateCalculatorMaxAttempts(MAX_NUMBER_OF_ATTEMPTS),
                new RecordEveryAttempt());
    }

    private static Predicate<LockoutRequest> buildAppliesToPolicyPredicate() {
        return new DefaultPolicyPredicateBuilder()
                .channelIds(Rsa.ID)
                .activityNames(OnlinePurchase.NAME)
                .aliasTypes(CreditCardNumber.TYPE, DebitCardNumber.TYPE)
                .build();
    }

}
