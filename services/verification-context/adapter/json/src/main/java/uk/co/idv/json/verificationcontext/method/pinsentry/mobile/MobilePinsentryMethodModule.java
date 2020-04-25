package uk.co.idv.json.verificationcontext.method.pinsentry.mobile;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.json.verificationcontext.method.VerificationMethodModule;
import uk.co.idv.json.verificationcontext.method.pinsentry.PinsentryFunctionSerializer;
import uk.co.idv.json.verificationcontext.method.pinsentry.PinsentryVerificationMethodMixin;
import uk.co.idv.json.verificationcontext.result.VerificationResultsModule;

import java.util.Arrays;

public class MobilePinsentryMethodModule extends SimpleModule {

    public MobilePinsentryMethodModule() {
        super("mobile-pinsentry-method-module", Version.unknownVersion());

        setMixInAnnotation(MobilePinsentry.class, PinsentryVerificationMethodMixin.class);

        addSerializer(PinsentryFunction.class, new PinsentryFunctionSerializer());

        addDeserializer(MobilePinsentry.class, new MobilePinsentryDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new VerificationMethodModule(),
                new VerificationResultsModule()
        );
    }

}
