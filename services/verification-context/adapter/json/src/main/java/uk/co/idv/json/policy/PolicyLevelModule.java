package uk.co.idv.json.policy;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.policy.PolicyLevel;

public class PolicyLevelModule extends SimpleModule {

    public PolicyLevelModule() {
        super("policy-level-module", Version.unknownVersion());

        setMixInAnnotation(PolicyLevel.class, PolicyLevelMixin.class);

        addDeserializer(PolicyLevel.class, new PolicyLevelDeserializer());
    }

}
