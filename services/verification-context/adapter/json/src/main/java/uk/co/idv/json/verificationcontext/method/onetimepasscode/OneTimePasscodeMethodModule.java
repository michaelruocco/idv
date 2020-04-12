package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;


public class OneTimePasscodeMethodModule extends SimpleModule {

    public OneTimePasscodeMethodModule() {
        super("one-time-passcode-method-module", Version.unknownVersion());

        addSerializer(OneTimePasscode.class, new OneTimePasscodeSerializer());
        addSerializer(PasscodeSettings.class, new PasscodeSettingsSerializer());

        addDeserializer(DeliveryMethod.class, new DeliveryMethodDeserializer());
        addDeserializer(PasscodeSettings.class, new PasscodeSettingsDeserializer());
    }

}
