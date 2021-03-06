package uk.co.idv.api.lockout;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.api.lockout.attempt.ApiVerificationAttemptMixin;
import uk.co.idv.api.lockout.attempt.VerificationAttemptsSerializer;
import uk.co.idv.api.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.api.lockout.policy.LockoutPolicyDocumentDeserializer;
import uk.co.idv.api.lockout.policy.ApiLockoutPolicyMixin;
import uk.co.idv.api.lockout.state.ApiHardLockoutStateMixin;
import uk.co.idv.api.lockout.state.LockoutStateDocument;
import uk.co.idv.api.lockout.state.LockoutStateDocumentMixin;
import uk.co.idv.api.lockout.state.ApiLockoutStateMixin;
import uk.co.idv.api.lockout.state.ResetLockoutStateDocument;
import uk.co.idv.api.lockout.state.ResetLockoutStateDocumentDeserializer;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;

public class ApiLockoutStateModule extends SimpleModule {

    public ApiLockoutStateModule() {
        setMixInAnnotation(LockoutPolicy.class, ApiLockoutPolicyMixin.class);
        setMixInAnnotation(LockoutState.class, ApiLockoutStateMixin.class);
        setMixInAnnotation(HardLockoutState.class, ApiHardLockoutStateMixin.class);
        setMixInAnnotation(LockoutStateDocument.class, LockoutStateDocumentMixin.class);
        setMixInAnnotation(VerificationAttempt.class, ApiVerificationAttemptMixin.class);

        addDeserializer(ResetLockoutStateDocument.class, new ResetLockoutStateDocumentDeserializer());
        addDeserializer(LockoutRequest.class, new LockoutRequestDeserializer());
        addDeserializer(LockoutPolicyDocument.class, new LockoutPolicyDocumentDeserializer());

        addSerializer(new VerificationAttemptsSerializer());
    }

}
