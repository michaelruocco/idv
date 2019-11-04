package uk.co.mruoc.idv.lockout.jsonapi;

import uk.co.mruoc.idv.lockout.api.LockoutStateModule;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutStateMaxAttempts;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

public class JsonApiLockoutStateModule extends LockoutStateModule {

    public JsonApiLockoutStateModule() {
        setMixInAnnotation(LockoutStateMaxAttempts.class, JsonApiLockoutStateMaxAttemptsMixin.class);
        setMixInAnnotation(LockoutPolicyParameters.class, JsonApiLockoutPolicyParametersMixin.class);

        addDeserializer(ResetLockoutStateDocument.class, new ResetLockoutStateDocumentDeserializer());
        addDeserializer(LockoutRequest.class, new LockoutRequestDeserializer());
    }

}
