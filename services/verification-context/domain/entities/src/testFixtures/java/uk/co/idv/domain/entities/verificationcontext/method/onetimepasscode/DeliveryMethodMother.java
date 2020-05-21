package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class DeliveryMethodMother {

    private static final UUID DEFAULT_ID = UUID.fromString("9c3fbf94-d4b7-445e-ae3a-b0935f314c6d");

    private DeliveryMethodMother() {
        // utility class
    }

    public static Collection<DeliveryMethod> oneSms() {
        return oneSms(DEFAULT_ID);
    }

    public static Collection<DeliveryMethod> oneSms(UUID id) {
        return Collections.singleton(sms(id));
    }

    public static SmsDeliveryMethod sms() {
        return sms(DEFAULT_ID);
    }

    public static SmsDeliveryMethod sms(UUID id) {
        return new SmsDeliveryMethod(id, "+447089111111");
    }

}
