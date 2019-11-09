package uk.co.idv.repository.mongo.verificationcontext.eligibility;

import lombok.Data;

@Data
public class EligibilityDocument {

    private boolean eligible;
    private String reason;

}
