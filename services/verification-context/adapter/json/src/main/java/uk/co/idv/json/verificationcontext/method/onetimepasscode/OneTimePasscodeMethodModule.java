package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.json.verificationcontext.method.VerificationMethodModule;
import uk.co.idv.json.verificationcontext.result.VerificationResultsModule;

import java.util.Arrays;


public class OneTimePasscodeMethodModule extends SimpleModule {

    public OneTimePasscodeMethodModule() {
        super("one-time-passcode-method-module", Version.unknownVersion());

        setMixInAnnotation(OneTimePasscode.class, OneTimePasscodeMixin.class);

        addDeserializer(OneTimePasscode.class, new OneTimePasscodeDeserializer());
        addDeserializer(DeliveryMethod.class, new DeliveryMethodDeserializer());
        addDeserializer(PasscodeSettings.class, new PasscodeSettingsDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new VerificationMethodModule(),
                new VerificationResultsModule()
        );
    }

}
