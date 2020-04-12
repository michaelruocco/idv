
package uk.co.idv.api.onetimepasscode;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.json.onetimepasscode.OneTimePasscodeModule;
import uk.co.mruoc.jsonapi.ApiModule;

import java.util.Arrays;

public class ApiOneTimePasscodeModule extends SimpleModule {

    public ApiOneTimePasscodeModule() {
        super("api-one-time-passcode-module", Version.unknownVersion());

        setMixInAnnotation(OneTimePasscodeVerification.class, ApiOneTimePasscodeVerificationMixin.class);

        addDeserializer(SendOneTimePasscodeRequestDocument.class, new SendOneTimePasscodeRequestDocumentDeserializer());
        addDeserializer(ResendOneTimePasscodeRequestDocument.class, new ResendOneTimePasscodeRequestDocumentDeserializer());
        addDeserializer(VerifyOneTimePasscodeRequestDocument.class, new VerifyOneTimePasscodeRequestDocumentDeserializer());
    }

    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new ApiModule(),
                new OneTimePasscodeModule()
        );
    }

}
