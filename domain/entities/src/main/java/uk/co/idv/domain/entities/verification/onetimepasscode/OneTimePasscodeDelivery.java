package uk.co.idv.domain.entities.verification.onetimepasscode;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;

@Builder
@Getter
@EqualsAndHashCode
public class OneTimePasscodeDelivery {

    private final DeliveryMethod method;
    private final String passcode;
    private final Activity activity;

    public boolean hasPasscode(final String passcode) {
        return this.passcode.equals(passcode);
    }

}
