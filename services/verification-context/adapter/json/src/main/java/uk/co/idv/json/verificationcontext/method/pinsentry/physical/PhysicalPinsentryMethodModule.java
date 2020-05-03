package uk.co.idv.json.verificationcontext.method.pinsentry.physical;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryFunction;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.json.cardnumber.CardNumberModule;
import uk.co.idv.json.verificationcontext.method.VerificationMethodModule;
import uk.co.idv.json.verificationcontext.method.pinsentry.PinsentryFunctionSerializer;
import uk.co.idv.json.verificationcontext.method.pinsentry.PinsentryParamsDeserializer;

import java.util.Arrays;

public class PhysicalPinsentryMethodModule extends SimpleModule {

    public PhysicalPinsentryMethodModule() {
        super("physical-pinsentry-method-module", Version.unknownVersion());

        setMixInAnnotation(PhysicalPinsentry.class, PhysicalPinsentryVerificationMethodMixin.class);

        addSerializer(PinsentryFunction.class, new PinsentryFunctionSerializer());

        addDeserializer(PhysicalPinsentry.class, new PhysicalPinsentryDeserializer());
        addDeserializer(PinsentryParams.class, new PinsentryParamsDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new CardNumberModule(),
                new VerificationMethodModule()
        );
    }

}
