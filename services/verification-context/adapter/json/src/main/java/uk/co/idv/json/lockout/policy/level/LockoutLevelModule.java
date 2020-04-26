package uk.co.idv.json.lockout.policy.level;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

public class LockoutLevelModule extends SimpleModule {

    public LockoutLevelModule() {
        super("lockout-level-module", Version.unknownVersion());

        setMixInAnnotation(LockoutLevel.class, LockoutLevelMixin.class);

        addDeserializer(LockoutLevel.class, new LockoutLevelDeserializer());
    }

}
