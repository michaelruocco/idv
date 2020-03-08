package uk.co.idv.domain.entities.verification.onetimepasscode;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class OneTimePasscodeDelivery {

    private final DeliveryMethod method;
    private final String passcode;
    private final Activity activity;
    //TODO add sent instant
    //TODO dont render activity when rendering delivery on API (update API module and add mixin to hide it)
    //in real life we might need to keep activity, but for this example it would be cleaner to convert activity into a message and just store that
    //TODO optionally add a message here as we should ideally control message text?

    public boolean hasPasscode(final String passcode) {
        return this.passcode.equals(passcode);
    }

}
