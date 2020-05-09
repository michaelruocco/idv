package uk.co.idv.api.verificationcontext.policy;

import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;

public class ApiVerificationPolicyModule extends SimpleModule {

    public ApiVerificationPolicyModule() {
        setMixInAnnotation(VerificationPolicy.class, ApiVerificationPolicyMixin.class);

        addDeserializer(VerificationPolicyDocument.class, new VerificationPolicyDocumentDeserializer());
    }

}
