package uk.co.mruoc.idv.mongo.verificationcontext.dao.eligibility;

import lombok.Data;

@Data
public class EligibilityDocument {

    private boolean eligible;
    private String reason;

}
