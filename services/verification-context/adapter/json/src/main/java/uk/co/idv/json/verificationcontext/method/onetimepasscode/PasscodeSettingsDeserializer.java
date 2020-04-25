package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DefaultPasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;

public class PasscodeSettingsDeserializer extends StdDeserializer<PasscodeSettings> {

    public PasscodeSettingsDeserializer() {
        super(PasscodeSettings.class);
    }

    @Override
    public PasscodeSettings deserialize(final JsonParser parser, final DeserializationContext context) {
        return new DefaultPasscodeSettings(); //TODO properly deserialize values
    }

}
