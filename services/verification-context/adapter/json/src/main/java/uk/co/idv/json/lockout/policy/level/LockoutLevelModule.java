package uk.co.idv.json.lockout.policy.level;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.policy.PolicyLevel;

public class LockoutLevelModule extends SimpleModule {

    public LockoutLevelModule() {
        super("lockout-level-module", Version.unknownVersion());

        setMixInAnnotation(PolicyLevel.class, LockoutLevelMixin.class);

        addDeserializer(PolicyLevel.class, new LockoutLevelDeserializer());
    }

}
