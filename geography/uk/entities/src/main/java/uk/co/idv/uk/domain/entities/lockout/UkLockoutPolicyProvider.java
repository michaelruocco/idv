package uk.co.idv.uk.domain.entities.lockout;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.activity.OnlinePurchase;
import uk.co.idv.uk.domain.entities.channel.Rsa;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
public class UkLockoutPolicyProvider implements LockoutPolicyProvider {

    @Override
    public Collection<LockoutPolicy> getPolicies() {
        return Arrays.asList(
                buildPolicy(CreditCardNumber.TYPE, UUID.fromString("d3bf531a-bdcd-45d5-b5b6-d7a213f3af7b")),
                buildPolicy(DebitCardNumber.TYPE, UUID.fromString("455a23f9-6505-491b-aa0f-2d4bf06acbbe"))
        );
    }

    private LockoutPolicy buildPolicy(final String aliasType, final UUID id) {
        return DefaultLockoutPolicy.builder()
                .level(buildLockoutLevel(aliasType))
                .stateCalculator(buildStateCalculator())
                .recordAttemptStrategy(buildRecordAttemptStrategy())
                .id(id)
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
