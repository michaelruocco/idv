package uk.co.mruoc.idv.mongo.verificationcontext.dao.result;

import lombok.Data;

@Data
public class VerificationResultDocument {

    private String methodName;
    private String verificationId;
    private String timestamp;
    private boolean successful;

}
