package uk.co.idv.repository.mongo.verificationcontext.method;

import lombok.Data;
import uk.co.idv.repository.mongo.verificationcontext.eligibility.EligibilityDocument;
import uk.co.idv.repository.mongo.verificationcontext.result.VerificationResultDocument;

import java.util.Collection;

@Data
public class VerificationMethodDocument {

    private String name;
    private Collection<VerificationResultDocument> results;
    private int maxAttempts;
    private long duration;
    private EligibilityDocument eligibility;

    public boolean isEligible() {
        return eligibility.isEligible();
    }

}
