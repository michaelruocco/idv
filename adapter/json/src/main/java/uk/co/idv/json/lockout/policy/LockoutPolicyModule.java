package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.json.lockout.attempt.VerificationAttemptDeserializer;
import uk.co.idv.json.lockout.attempt.VerificationAttemptMixin;
import uk.co.idv.json.lockout.attempt.VerificationAttemptsDeserializer;
import uk.co.idv.json.lockout.attempt.VerificationAttemptsSerializer;

public class LockoutPolicyModule extends SimpleModule {

    public LockoutPolicyModule() {
        setMixInAnnotation(LockoutLevel.class, LockoutLevelMixin.class);
        setMixInAnnotation(LockoutPolicy.class, LockoutPolicyMixin.class);
    }

}
