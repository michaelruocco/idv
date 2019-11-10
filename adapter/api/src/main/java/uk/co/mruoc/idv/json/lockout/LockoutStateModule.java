package uk.co.mruoc.idv.json.lockout;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.state.LockoutStateMaxAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

public class LockoutStateModule extends SimpleModule {

    public LockoutStateModule() {
        setMixInAnnotation(LockoutStateMaxAttempts.class, LockoutStateMaxAttemptsMixin.class);
        setMixInAnnotation(VerificationAttempt.class, VerificationAttemptMixin.class);
        setMixInAnnotation(DefaultLockoutPolicyParameters.class, LockoutPolicyParametersMixin.class);
        setMixInAnnotation(LockoutLevel.class, LockoutLevelMixin.class);

        addSerializer(VerificationAttempts.class, new VerificationAttemptsSerializer());
    }

}