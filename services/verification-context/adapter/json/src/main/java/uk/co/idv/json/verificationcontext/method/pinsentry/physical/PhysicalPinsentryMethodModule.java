package uk.co.idv.json.verificationcontext.method.pinsentry.physical;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;

public class PhysicalPinsentryMethodModule extends SimpleModule {

    public PhysicalPinsentryMethodModule() {
        super("physical-pinsentry-method-module", Version.unknownVersion());

        addSerializer(PhysicalPinsentry.class, new PhysicalPinsentrySerializer());
    }

}
