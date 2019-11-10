package uk.co.idv.api.lockout;

import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.json.lockout.LockoutStateModule;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.state.LockoutStateMaxAttempts;

public class JsonApiLockoutStateModule extends LockoutStateModule {

    public JsonApiLockoutStateModule() {
        setMixInAnnotation(LockoutStateMaxAttempts.class, JsonApiLockoutStateMaxAttemptsMixin.class);
        setMixInAnnotation(DefaultLockoutPolicyParameters.class, JsonApiLockoutPolicyParametersMixin.class);

        addDeserializer(ResetLockoutStateDocument.class, new ResetLockoutStateDocumentDeserializer());
        addDeserializer(LockoutRequest.class, new LockoutRequestDeserializer());
    }

}
