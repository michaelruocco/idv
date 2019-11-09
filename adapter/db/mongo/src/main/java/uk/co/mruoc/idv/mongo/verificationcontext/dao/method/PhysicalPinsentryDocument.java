package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PinsentryFunction;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class PhysicalPinsentryDocument extends VerificationMethodDocument {

    private PinsentryFunction function;
    private Collection<CardNumberDocument> cardNumbers;

}
