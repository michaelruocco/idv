package uk.co.mruoc.idv.lockout.jsonapi;

import uk.co.mruoc.idv.lockout.api.LockoutStateModule;
import uk.co.mruoc.idv.lockout.domain.model.LockoutStateMaxAttempts;

public class JsonApiLockoutStateModule extends LockoutStateModule {

    public JsonApiLockoutStateModule() {
        setMixInAnnotation(LockoutStateMaxAttempts.class, JsonApiLockoutStateMaxAttemptsMixin.class);
    }

}
