package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.VerificationMethodDocument;

import java.util.Collection;


@Getter
@Builder
public class VerificationSequenceDocument {

    private final String name;
    private final Collection<VerificationMethodDocument> methods;

}
