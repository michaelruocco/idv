package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class DeliveryMethodMother {

    private static final UUID DEFAULT_ID = UUID.fromString("2a82fcb5-19d4-469d-9c1b-4b2318c1e3f4");

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
