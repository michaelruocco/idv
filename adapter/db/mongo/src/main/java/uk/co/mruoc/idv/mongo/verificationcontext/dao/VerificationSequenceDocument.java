package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import lombok.Data;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.VerificationMethodDocument;

import java.util.Collection;

@Data
public class VerificationSequenceDocument {

    private String name;
    private Collection<VerificationMethodDocument> methods;

}
