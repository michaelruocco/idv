package uk.co.idv.repository.mongo.verificationcontext.method.pinsentry.mobile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PinsentryFunction;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocument;

@Data
@EqualsAndHashCode(callSuper = true)
public class MobilePinsentryDocument extends VerificationMethodDocument {

    private PinsentryFunction function;

}
