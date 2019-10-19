package uk.co.mruoc.idv.mongo.lockout.dao;

import lombok.Data;
import uk.co.mruoc.idv.mongo.identity.dao.AliasDocument;

@Data
public class VerificationAttemptDocument {

    private String contextId;
    private String channelId;
    private String activityName;
    private AliasDocument alias;
    private String idvIdValue;
    private String methodName;
    private String verificationId;
    private String timestamp;
    private boolean successful;

}
