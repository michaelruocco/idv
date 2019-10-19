package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.Data;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.EligibilityDocument;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultDocument;

import java.util.Collection;

@Data
public class VerificationMethodDocument {

    private String name;
    private Collection<VerificationResultDocument> results;
    private int maxAttempts;
    private String duration;
    private EligibilityDocument eligibility;

    public boolean isEligible() {
        return eligibility.isEligible();
    }

}
