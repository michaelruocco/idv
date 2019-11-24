package uk.co.idv.api.lockout;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.api.lockout.attempt.VerificationAttemptMixin;
import uk.co.idv.api.lockout.attempt.VerificationAttemptsSerializer;
import uk.co.idv.api.lockout.policy.hard.HardLockoutStateMixin;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributesMixin;
import uk.co.idv.api.lockout.state.LockoutStateMixin;
import uk.co.idv.api.lockout.state.ResetLockoutStateDocument;
import uk.co.idv.api.lockout.state.ResetLockoutStateDocumentDeserializer;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.api.lockout.policy.DefaultLockoutPolicyAttributes;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;

public class LockoutStateModule extends SimpleModule {

    public LockoutStateModule() {
        setMixInAnnotation(LockoutState.class, LockoutStateMixin.class);
        setMixInAnnotation(HardLockoutState.class, HardLockoutStateMixin.class);
        setMixInAnnotation(DefaultLockoutPolicyAttributes.class, LockoutPolicyAttributesMixin.class);
        setMixInAnnotation(VerificationAttempt.class, VerificationAttemptMixin.class);
        setMixInAnnotation(LockoutLevel.class, LockoutLevelMixin.class);

        addDeserializer(ResetLockoutStateDocument.class, new ResetLockoutStateDocumentDeserializer());
        addDeserializer(LockoutRequest.class, new LockoutRequestDeserializer());

        addSerializer(VerificationAttempts.class, new VerificationAttemptsSerializer());
    }

}
