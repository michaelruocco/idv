package uk.co.mruoc.idv.api.lockout;

import uk.co.mruoc.idv.json.lockout.LockoutStateModule;
import uk.co.idv.domain.entities.lockout.DefaultLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.LockoutStateMaxAttempts;
import uk.co.idv.domain.usecases.lockout.LockoutRequest;

public class JsonApiLockoutStateModule extends LockoutStateModule {

    public JsonApiLockoutStateModule() {
        setMixInAnnotation(LockoutStateMaxAttempts.class, JsonApiLockoutStateMaxAttemptsMixin.class);
        setMixInAnnotation(DefaultLockoutPolicyParameters.class, JsonApiLockoutPolicyParametersMixin.class);

        addDeserializer(ResetLockoutStateDocument.class, new ResetLockoutStateDocumentDeserializer());
        addDeserializer(LockoutRequest.class, new LockoutRequestDeserializer());
    }

}
