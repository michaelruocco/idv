package uk.co.mruoc.idv.lockout.api;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.mruoc.idv.lockout.domain.model.AbstractLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutStateMaxAttempts;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

public class LockoutStateModule extends SimpleModule {

    public LockoutStateModule() {
        setMixInAnnotation(LockoutStateMaxAttempts.class, LockoutStateMaxAttemptsMixin.class);
        setMixInAnnotation(VerificationAttempt.class, VerificationAttemptMixin.class);
        setMixInAnnotation(AbstractLockoutPolicyParameters.class, LockoutPolicyParametersMixin.class);

        addSerializer(VerificationAttempts.class, new VerificationAttemptsSerializer());
    }

}
