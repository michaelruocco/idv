package uk.co.idv.json.verificationcontext.method;

import uk.co.idv.json.verificationcontext.method.cardcredentials.CardCredentialsJsonNodeConverter;
import uk.co.idv.json.verificationcontext.method.onetimepasscode.OneTimePasscodeSmsJsonNodeConverter;
import uk.co.idv.json.verificationcontext.method.pinsentry.mobile.MobilePinsentryJsonNodeConverter;
import uk.co.idv.json.verificationcontext.method.pinsentry.physical.PhysicalPinsentryJsonNodeConverter;
import uk.co.idv.json.verificationcontext.method.pushnotification.PushNotificationJsonNodeConverter;

public class DefaultVerificationMethodDeserializer extends VerificationMethodDeserializer {

    public DefaultVerificationMethodDeserializer() {
        super(
                new PushNotificationJsonNodeConverter(),
                new PhysicalPinsentryJsonNodeConverter(),
                new MobilePinsentryJsonNodeConverter(),
                new OneTimePasscodeSmsJsonNodeConverter(),
                new CardCredentialsJsonNodeConverter()
        );
    }

}
