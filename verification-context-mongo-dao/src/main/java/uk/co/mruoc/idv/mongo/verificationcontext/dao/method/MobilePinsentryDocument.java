package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PinsentryFunction;

@Data
@EqualsAndHashCode(callSuper = true)
public class MobilePinsentryDocument extends VerificationMethodDocument {

    private PinsentryFunction function;

}
