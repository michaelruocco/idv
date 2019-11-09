package uk.co.idv.repository.mongo.verificationcontext;

import lombok.Data;
import uk.co.idv.repository.mongo.verificationcontext.method.VerificationMethodDocument;

import java.util.Collection;

@Data
public class VerificationSequenceDocument {

    private String name;
    private Collection<VerificationMethodDocument> methods;

}
