package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.json.verificationcontext.method.VerificationMethodSerializer;

public class AllowAllSimpleMethodModule extends SimpleModule {

    public AllowAllSimpleMethodModule() {
        super("allow-all-simple-method-module", Version.unknownVersion());

        addSerializer(VerificationMethod.class, new VerificationMethodSerializer());
    }

}
