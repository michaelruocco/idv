package uk.co.idv.domain.entities.verification.onetimepasscode;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;

@Builder
@Getter
public class OneTimePasscodeGeneration {

    private final DeliveryMethod deliveryMethod;
    private final String passcode;

    public boolean isValid(final String passcode) {
        return this.passcode.equals(passcode);
    }

}
