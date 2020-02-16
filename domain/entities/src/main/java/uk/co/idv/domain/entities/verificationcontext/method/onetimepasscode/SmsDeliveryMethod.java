package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class SmsDeliveryMethod implements DeliveryMethod {

    public static final String TYPE = "sms";

    private final UUID id;
    private final String value;

    public SmsDeliveryMethod(final String value) {
        this(UUID.randomUUID(), value);
    }

    public String getType() {
        return TYPE;
    }

}
