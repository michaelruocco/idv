package uk.co.idv.domain.entities.lockout.uk;

import lombok.Getter;
import uk.co.idv.domain.entities.lockout.MaxAttemptsAliasLevelLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.RecordEveryAttempt;
import uk.co.idv.domain.entities.channel.Rsa;
import uk.co.idv.domain.entities.activity.OnlinePurchase;

import java.util.UUID;

@Getter
public class RsaMaxAttemptsAliasLevelLockoutPolicyParameters extends MaxAttemptsAliasLevelLockoutPolicyParameters {

    private static final int MAX_ATTEMPTS = 3;

    public RsaMaxAttemptsAliasLevelLockoutPolicyParameters(final String aliasType) {
        this(aliasType, UUID.randomUUID());
    }

    public RsaMaxAttemptsAliasLevelLockoutPolicyParameters(final String aliasType, final UUID id) {
        super(id,
                RecordEveryAttempt.TYPE,
                Rsa.ID,
                OnlinePurchase.NAME,
                aliasType,
                MAX_ATTEMPTS);
    }

}
