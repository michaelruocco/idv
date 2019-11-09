package uk.co.mruoc.idv.lockout.domain.model.uk;

import lombok.Getter;
import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;
import uk.co.mruoc.idv.domain.model.channel.Rsa;
import uk.co.mruoc.idv.lockout.domain.model.MaxAttemptsAliasLevelLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.RecordEveryAttempt;

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
