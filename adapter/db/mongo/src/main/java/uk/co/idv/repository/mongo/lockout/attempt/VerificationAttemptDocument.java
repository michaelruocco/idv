package uk.co.idv.repository.mongo.lockout.attempt;

import lombok.Data;
import uk.co.idv.repository.mongo.identity.alias.AliasDocument;

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
