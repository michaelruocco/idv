package uk.co.idv.api.lockout;

import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.json.lockout.LockoutStateModule;
import uk.co.idv.json.lockout.DefaultLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutState;

public class JsonApiLockoutStateModule extends LockoutStateModule {

    public JsonApiLockoutStateModule() {
        setMixInAnnotation(HardLockoutState.class, JsonApiLockoutHardLockoutStateMixin.class);
        setMixInAnnotation(DefaultLockoutPolicyParameters.class, JsonApiLockoutPolicyParametersMixin.class);

        addDeserializer(ResetLockoutStateDocument.class, new ResetLockoutStateDocumentDeserializer());
        addDeserializer(LockoutRequest.class, new LockoutRequestDeserializer());
    }

}
