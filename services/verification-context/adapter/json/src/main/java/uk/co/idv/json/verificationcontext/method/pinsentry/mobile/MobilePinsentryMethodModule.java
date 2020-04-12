package uk.co.idv.json.verificationcontext.method.pinsentry.mobile;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;

public class MobilePinsentryMethodModule extends SimpleModule {

    public MobilePinsentryMethodModule() {
        super("mobile-pinsentry-method-module", Version.unknownVersion());

        addSerializer(MobilePinsentry.class, new MobilePinsentrySerializer());
    }

}
