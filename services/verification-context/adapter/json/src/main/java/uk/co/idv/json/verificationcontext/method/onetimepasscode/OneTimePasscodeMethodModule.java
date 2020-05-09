package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.PasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.policy.OneTimePasscodePolicy;
import uk.co.idv.json.verificationcontext.method.VerificationMethodModule;

import java.util.Collections;


public class OneTimePasscodeMethodModule extends SimpleModule {

    public OneTimePasscodeMethodModule() {
        super("one-time-passcode-method-module", Version.unknownVersion());

        setMixInAnnotation(OneTimePasscode.class, OneTimePasscodeMixin.class);
        setMixInAnnotation(OneTimePasscodeParams.class, OneTimePasscodeParamsMixin.class);

        addDeserializer(OneTimePasscode.class, new OneTimePasscodeDeserializer());
        addDeserializer(DeliveryMethod.class, new DeliveryMethodDeserializer());
        addDeserializer(OneTimePasscodeParams.class, new OneTimePasscodeParamsDeserializer());
        addDeserializer(PasscodeParams.class, new PasscodeSettingsDeserializer());
        addDeserializer(OneTimePasscodePolicy.class, new OneTimePasscodePolicyDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new VerificationMethodModule());
    }

}
