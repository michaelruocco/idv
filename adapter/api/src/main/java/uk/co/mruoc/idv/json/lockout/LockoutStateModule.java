package uk.co.mruoc.idv.json.lockout;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.DefaultLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.LockoutStateMaxAttempts;
import uk.co.idv.domain.entities.lockout.VerificationAttempt;
import uk.co.idv.domain.entities.lockout.VerificationAttempts;

public class LockoutStateModule extends SimpleModule {

    public LockoutStateModule() {
        setMixInAnnotation(LockoutStateMaxAttempts.class, LockoutStateMaxAttemptsMixin.class);
        setMixInAnnotation(VerificationAttempt.class, VerificationAttemptMixin.class);
        setMixInAnnotation(DefaultLockoutPolicyParameters.class, LockoutPolicyParametersMixin.class);

        addSerializer(VerificationAttempts.class, new VerificationAttemptsSerializer());
    }

}
