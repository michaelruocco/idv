package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.Map;


@Getter
@Builder
public class VerificationMethodDocument {

    private final String name;
    private final Collection<VerificationResultDocument> results;
    private final int maxAttempts;
    private final String duration;
    private final EligibilityDocument eligibility;
    private final Map<String, String> properties;

    public boolean isEligible() {
        return eligibility.isEligible();
    }

}
