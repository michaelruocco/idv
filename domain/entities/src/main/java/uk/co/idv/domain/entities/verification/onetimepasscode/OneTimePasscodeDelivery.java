package uk.co.idv.domain.entities.verification.onetimepasscode;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;

import java.time.Instant;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class OneTimePasscodeDelivery {

    private final DeliveryMethod method;
    private final String passcode;
    private final String message;
    private final Instant sent;

    public boolean hasPasscode(final String passcode) {
        return this.passcode.equals(passcode);
    }

}
