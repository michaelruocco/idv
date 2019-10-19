package uk.co.mruoc.idv.mongo.verificationcontext.dao;

import lombok.Data;

@Data
public class EligibilityDocument {

    private boolean eligible;
    private String reason;

}
