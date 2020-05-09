package uk.co.idv.json.verificationcontext.policy;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationSequencePolicy;
import uk.co.idv.json.DurationSerializer;
import uk.co.idv.json.policy.PolicyLevelModule;

import java.time.Duration;
import java.util.Collections;

public class VerificationPolicyModule extends SimpleModule {

    public VerificationPolicyModule() {
        super("verification-policy-module", Version.unknownVersion());

        setMixInAnnotation(VerificationPolicy.class, VerificationPolicyMixin.class);
        setMixInAnnotation(VerificationSequencePolicy.class, VerificationSequencePolicyMixin.class);
        setMixInAnnotation(VerificationMethodPolicy.class, VerificationMethodPolicyMixin.class);

        addSerializer(Duration.class, new DurationSerializer());

        addDeserializer(VerificationPolicy.class, new VerificationPolicyDeserializer());
        addDeserializer(VerificationSequencePolicy.class, new VerificationSequencePolicyDeserializer());
        addDeserializer(VerificationMethodPolicy.class, new VerificationMethodPolicyDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new PolicyLevelModule());
    }

}
