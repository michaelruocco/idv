package uk.co.idv.repository.mongo.verificationcontext.result;

import lombok.Data;

@Data
public class VerificationResultDocument {

    private String methodName;
    private String verificationId;
    private String timestamp;
    private boolean successful;

}
