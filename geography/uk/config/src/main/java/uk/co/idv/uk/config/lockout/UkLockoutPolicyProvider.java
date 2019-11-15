package uk.co.idv.uk.config.lockout;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.activity.OnlinePurchase;
import uk.co.idv.domain.entities.channel.Rsa;
import uk.co.idv.domain.entities.lockout.policy.AliasLockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.identity.alias.CreditCardNumber;
import uk.co.idv.domain.entities.identity.alias.DebitCardNumber;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.domain.entities.lockout.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.state.MaxAttemptsLockoutStateCalculator;
import uk.co.idv.domain.usecases.util.IdGenerator;

import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public class UkLockoutPolicyProvider implements LockoutPolicyProvider {

    private final IdGenerator idGenerator;

    @Override
    public Collection<LockoutPolicy> getPolicies() {
        return Arrays.asList(
                buildPolicy(CreditCardNumber.TYPE),
                buildPolicy(DebitCardNumber.TYPE)
        );
    }

    private LockoutPolicy buildPolicy(final String aliasType) {
        return DefaultLockoutPolicy.builder()
                .level(buildLockoutLevel(aliasType))
                .stateCalculator(buildStateCalculator())
                .recordAttemptStrategy(buildRecordAttemptStrategy())
                .id(idGenerator.generate())
                .build();
    }

    private static LockoutLevel buildLockoutLevel(final String aliasType) {
        return AliasLockoutLevel.builder()
                .channelId(Rsa.ID)
                .activityName(OnlinePurchase.NAME)
                .aliasType(aliasType)
                .build();
    }

    private static LockoutStateCalculator buildStateCalculator() {
        return new MaxAttemptsLockoutStateCalculator(3);
    }

    private static RecordAttemptStrategy buildRecordAttemptStrategy() {
        return new RecordEveryAttempt();
    }

}
