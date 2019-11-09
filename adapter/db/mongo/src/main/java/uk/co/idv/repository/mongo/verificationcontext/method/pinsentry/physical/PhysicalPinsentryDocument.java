package uk.co.idv.repository.mongo.verificationcontext.method.pinsentry.physical;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocument;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.PinsentryFunction;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class PhysicalPinsentryDocument extends VerificationMethodDocument {

    private PinsentryFunction function;
    private Collection<CardNumberDocument> cardNumbers;

}
