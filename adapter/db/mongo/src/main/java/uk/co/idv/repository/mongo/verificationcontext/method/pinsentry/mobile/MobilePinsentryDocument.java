package uk.co.idv.repository.mongo.verificationcontext.method.pinsentry.mobile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocument;
import uk.co.idv.domain.entities.verificationcontext.method.PinsentryFunction;

@Data
@EqualsAndHashCode(callSuper = true)
public class MobilePinsentryDocument extends VerificationMethodDocument {

    private PinsentryFunction function;

}
