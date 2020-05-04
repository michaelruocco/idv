package uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class SmsDeliveryMethod implements DeliveryMethod {

    public static final String TYPE = "sms";

    private final UUID id;
    private final String value;

    public String getType() {
        return TYPE;
    }

}
