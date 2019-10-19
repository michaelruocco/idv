package uk.co.mruoc.idv.mongo.verificationcontext.dao.result;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VerificationResultDocument {

    private final String methodName;
    private final String verificationId;
    private final String timestamp;
    private final boolean successful;

}
